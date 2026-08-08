# Digital Payment Platform

A modular monolith built with Spring Boot that lets users pay bills (water, electricity, gas, telephone, internet) using a personal wallet, credit cards, or a split between the two. Payment is accepted and initialized synchronously, then confirmed asynchronously once Stripe responds via webhook. Settlement processing is likewise fault-tolerant and asynchronous, so that no bill payment is ever lost or duplicated.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
  - [Modular Monolith](#modular-monolith)
  - [Hexagonal Architecture per Module](#hexagonal-architecture-per-module)
  - [Modules](#modules)
- [Core Flows](#core-flows)
  - [Authentication & Authorization](#authentication--authorization)
  - [Wallet](#wallet)
  - [Payments](#payments)
  - [Settlements](#settlements)
  - [Idempotency](#idempotency)
  - [Notifications](#notifications)
  - [Audit Logging](#audit-logging)
- [Database Schema](#database-schema)
- [Tech Stack](#tech-stack)
- [Deployment & CI/CD](#deployment--cicd)

## Overview

The application provides a single platform where users can:

- Top up a personal wallet and use it to pay bills.
- Add multiple credit cards and pay with them via Stripe.
- Pay a bill using wallet only, card only, or a split of both.
- Track the status of every payment, top-up, and settlement in real time.

Every action in the system is auditable, every payment is idempotent, and every settlement is guaranteed to either succeed, fail visibly, or be retried — nothing is silently dropped.

## Architecture

### Modular Monolith

The application is a **modular monolith** built with **Spring Modulith**. Modules are deployed as a single unit but are logically isolated, communicating with each other primarily through **domain events** rather than direct method calls or shared state. This keeps module boundaries explicit and makes it straightforward to extract a module into its own service later if needed.

### Hexagonal Architecture per Module
 
Each module internally follows **Hexagonal (Ports & Adapters) Architecture**, split into four layers:
 
- **Domain layer** — core business logic and rules, free of framework concerns.
- **Application layer** — use cases / ports that orchestrate the domain.
- **Infrastructure layer** — outbound adapters implementing the ports (JPA repositories, Stripe client, RabbitMQ publishers, cache, etc.).
- **API layer** — inbound adapters exposing the module (REST controllers).


This keeps business logic decoupled from Spring, persistence, and third-party SDKs, and makes each module independently testable.


### Modules

| Module | Responsibility |
|---|---|
| **Identity** | User registration/authentication, JWT issuance, role-based authorization |
| **Wallet** | Wallet balance, top-ups, wallet transactions |
| **Billing** | Lists billers/providers and fetches bills on the user's behalf |
| **Payment** | Orchestrates the payment flow across wallet/card, integrates with Stripe |
| **Settlement** | Collects completed payments and settles funds with the billing provider |
| **Notification** | Streams real-time events to users (payment success/failure, top-ups, etc.) |
| **Audit** | Records every significant action taken across the system |

Modules communicate cross-cutting concerns (e.g. "a bill was paid", "a wallet was debited") via **Spring Modulith events** rather than direct calls, keeping them loosely coupled.

## Core Flows

### Authentication & Authorization

- Authentication is based on **short-lived JWT access tokens**.
- Authorization is **role-based** (`USER`, `ADMIN`, `SUPERUSER`), enforced at the API layer via Spring Security.

### Wallet

- Every user owns a wallet that can be topped up.
- Wallet balance can be used to pay bills.

### Payments

Payments can be funded in three ways:

1. **Wallet only** — if the balance covers the full amount.
2. **Card only** — charged directly via Stripe.
3. **Split** — part of the amount is deducted from the wallet, the remainder is charged to the card.

**Synchronous phase (transactional):**

When a payment is initiated, the following records are created in a **single database transaction** so the whole operation is atomic — if any step fails, everything rolls back and the payment attempt fails cleanly:

- A `Transaction` record.
- A `BillPayment` record linking the transaction to the bill.
- A `WalletTransaction` record (only when the wallet is used as a funding source).

Creating the `Transaction` also triggers a **synchronous domain event** to the Billing and Wallet modules so they have the context needed to later update their respective records once the payment outcome is known.

**Asynchronous phase (Stripe webhook):**

Card charges are confirmed asynchronously via a **Stripe webhook**:

- On receiving a webhook call, the application immediately publishes an internal asynchronous event and returns `200 OK` to Stripe right away — this avoids Stripe retrying the request due to slow processing and prevents duplicate webhook deliveries from being treated as new events.
- Duplicate webhook events are detected and discarded.
- The actual transaction/bill/wallet state updates happen asynchronously, driven by the outcome (success/failure) carried in the webhook event.

### Settlements

Once a payment is confirmed successful, it needs to be **settled** with the biller's payment provider. This is handled by a dedicated, fault-tolerant pipeline:

1. **Settlement creation** — a successful payment produces a `Settlement` record with status `PENDING`.
2. **Outbox scheduler (hourly)** — a scheduler runs every hour, picks up new/pending settlements, and inserts corresponding rows into a `settlements_outbox` table. This outbox table decouples "a settlement exists" from "a settlement has been queued for processing."
3. **Queueing scheduler (daily)** — a second scheduler runs once a day, reads pending rows from the outbox table, and publishes them to a **RabbitMQ** queue.
4. **Worker processing** — background workers consume the queue, call the relevant provider's API to actually pay the bill, and update the settlement status accordingly.
5. **Failure handling** — if a settlement fails at the provider step, it's moved into a `failed_settlements` table with a failure reason, so an admin can investigate, resolve, retry, or write it off manually.

This two-table (`settlements` / `settlements_outbox`) design with an explicit `failed_settlements` table ensures settlements are **fault-tolerant**: no payment is ever silently lost, only retried, queued, or flagged for manual resolution.

### Idempotency

Every payment request carries an **idempotency key**. Before processing:

1. The **cache** is checked first for a matching key.
2. If not found in cache, the **database** is checked, which is the ultimate source of truth (the idempotency key column has a uniqueness constraint).

This two-layer check ensures a bill can never be paid twice, even under retries, network issues, or duplicate client requests.

### Notifications

Real-time updates (payment success, payment failure, wallet top-up success, etc.) are streamed to the client using **Server-Sent Events (SSE)**, giving users live feedback without polling.

### Audit Logging

The **Audit** module records every significant action taken in the system (creates, updates, deletes, logins, logouts, payments), including old/new values, actor, and metadata — providing a full trail for compliance and investigation.

## Database Schema

The full entity-relationship schema (users, wallets, transactions, bills, settlements, outbox, failed settlements, notifications, audit logs, etc.) is defined separately.

📎 **Database schema:** [Digital Payment](https://dbdiagram.io/d/Digital-Payment-69b466eafb2db18e3b774b26)

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Persistence | Spring Data JPA, PostgreSQL 17 |
| Modularity | Spring Modulith (events, JDBC event publication) |
| Migrations | Flyway |
| Caching | Redis 7 |
| Security | Spring Security, JWT |
| Messaging | RabbitMQ (Spring AMQP) |
| Payments | Stripe Java SDK |
| API Docs | springdoc-openapi (Swagger UI) |
| Testing | Spring Boot Test, Spring Security Test, Spring Data JPA Test, Spring AMQP Test |

## Deployment & CI/CD

- The application is **containerized with Docker**.
- Deployed on **AWS** using **ECS** (Elastic Container Service) with images stored in **ECR** (Elastic Container Registry).
- A **CI/CD pipeline** runs automated tests on every change and handles deployments to AWS.
- Database schema changes are version-controlled and applied via **Flyway** migrations as part of the deployment process.
