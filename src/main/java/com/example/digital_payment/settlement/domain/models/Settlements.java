package com.example.digital_payment.settlement.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.settlement.domain.enums.SettlementStatus;
import com.example.digital_payment.settlement.domain.snapshots.SettlementSnapshot;
import com.example.digital_payment.settlement.domain.valueobjects.SettlementCreationData;

public class Settlements {
    private UUID id;
    private UUID billId;
    private UUID userId;
    private String customerNumber;
    private BigDecimal amount;
    private String currency;
    private SettlementStatus status;
    private UUID providerIdempotencyKey;
    private String providerReference;
    private int attemptCount;
    private LocalDateTime lastAttemptedAt;
    private String lastError;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;

    public Settlements() {

    }

    public static Settlements create(SettlementCreationData creationData) {
        Settlements settlement = new Settlements();
        settlement.id = UUID.randomUUID();
        settlement.billId = creationData.billId();
        settlement.userId = creationData.userId();
        settlement.customerNumber = creationData.customerNumber();
        settlement.amount = creationData.amount();
        settlement.currency = creationData.currency().toUpperCase().trim();
        settlement.status = SettlementStatus.PENDING;
        settlement.providerIdempotencyKey = UUID.randomUUID();
        settlement.providerReference = null;
        settlement.attemptCount = 0;
        settlement.lastAttemptedAt = null;
        settlement.lastError = null;
        settlement.processedAt = null;
        settlement.createdAt = LocalDateTime.now();
        return settlement;
    }

    public static Settlements reconstitute(SettlementSnapshot snapshot) {
        Settlements settlement = new Settlements();
        settlement.id = snapshot.id();
        settlement.billId = snapshot.billId();
        settlement.userId = snapshot.userId();
        settlement.customerNumber = snapshot.customerNumber();
        settlement.amount = snapshot.amount();
        settlement.currency = snapshot.currency();
        settlement.status = snapshot.status();
        settlement.providerIdempotencyKey = snapshot.providerIdempotencyKey();
        settlement.providerReference = snapshot.providerReference();
        settlement.attemptCount = snapshot.attemptCount();
        settlement.lastAttemptedAt = snapshot.lastAttemptedAt();
        settlement.lastError = snapshot.lastError();
        settlement.processedAt = snapshot.processedAt();
        settlement.createdAt = snapshot.createdAt();
        return settlement;
    }

    public void markProcessing() {
        if (this.status == SettlementStatus.SUCCEEDED
            || this.status == SettlementStatus.PROCESSING) {
            return;
        }

        this.status = SettlementStatus.PROCESSING;
        this.attemptCount++;
        this.lastAttemptedAt = LocalDateTime.now();
    }

    public void markSucceeded(String providerReference) {
        if (this.status != SettlementStatus.PROCESSING) {
            return;
        }

        this.status = SettlementStatus.SUCCEEDED;
        this.providerReference = providerReference;
        this.lastError = null;
        this.processedAt = LocalDateTime.now();
    }

    public void markFailed(String error, int maxAttempts) {
        if (this.status != SettlementStatus.PROCESSING) {
            return;
        }

        this.status = this.attemptCount >= maxAttempts ? SettlementStatus.FAILED
            : SettlementStatus.PROCESSING;
        this.lastError = error;
        this.processedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getBillId() {
        return billId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public SettlementStatus getStatus() {
        return status;
    }

    public UUID getProviderIdempotencyKey() {
        return providerIdempotencyKey;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public LocalDateTime getLastAttemptedAt() {
        return lastAttemptedAt;
    }

    public String getLastError() {
        return lastError;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
