package com.example.digital_payment.settlement.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.settlement.domain.enums.OutboxStatus;
import com.example.digital_payment.settlement.domain.snapshots.SettlementOutboxSnapshot;
import com.example.digital_payment.settlement.domain.valueobjects.SettlementOutboxCreationData;

public class SettlementsOutbox {
    private UUID id;
    private UUID settlementId;
    private OutboxStatus status;
    private int attemptCount;
    private String lastError;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;

    public SettlementsOutbox() {

    }

    public static SettlementsOutbox create(SettlementOutboxCreationData creationData) {
        SettlementsOutbox outbox = new SettlementsOutbox();
        outbox.id = UUID.randomUUID();
        outbox.settlementId = creationData.settlementId();
        outbox.status = OutboxStatus.PENDING;
        outbox.attemptCount = 0;
        outbox.lastError = null;
        outbox.createdAt = LocalDateTime.now();
        outbox.publishedAt = null;
        return outbox;
    }

    public static SettlementsOutbox reconstitute(SettlementOutboxSnapshot snapshot) {
        SettlementsOutbox outbox = new SettlementsOutbox();
        outbox.id = snapshot.id();
        outbox.settlementId = snapshot.settlementId();
        outbox.status = snapshot.status();
        outbox.attemptCount = snapshot.attemptCount();
        outbox.lastError = snapshot.lastError();
        outbox.createdAt = snapshot.createdAt();
        outbox.publishedAt = snapshot.publishedAt();
        return outbox;
    }

    public void markPublished() {
        if (this.status == OutboxStatus.PUBLISHED) {
            return;
        }

        this.status = OutboxStatus.PUBLISHED;
        this.attemptCount++;
        this.lastError = null;
        this.publishedAt = LocalDateTime.now();
    }

    public void markFailed(String error, int maxAttempts) {
        if (this.status == OutboxStatus.PUBLISHED) {
            return;
        }

        this.attemptCount++;
        this.lastError = error;
        this.status = attemptCount >= maxAttempts ? OutboxStatus.FAILED : OutboxStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSettlementId() {
        return settlementId;
    }

    public OutboxStatus getStatus() {
        return status;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public String getLastError() {
        return lastError;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

}
