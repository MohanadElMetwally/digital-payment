package com.example.digital_payment.payment.domain.model.entities;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;
import com.example.digital_payment.payment.domain.enums.TransactionType;
import com.example.digital_payment.payment.domain.model.snapshots.TransactionSnapshot;
import com.example.digital_payment.payment.domain.model.valueobjects.TransactionCreationData;

public class Transactions {
    private UUID id;
    private UUID userId;
    private UUID idempotencyKey;
    private String referenceNumber;
    private TransactionType type;
    private TransactionStatus status;
    private BigDecimal amount;
    private String currency;
    private String externalReference;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public Transactions() {

    }

    public static Transactions create(TransactionCreationData creationData) {
        Transactions tx = new Transactions();
        tx.id = UUID.randomUUID();
        tx.userId = creationData.userId();
        tx.idempotencyKey = creationData.idempotencyKey();
        tx.referenceNumber = generateReferenceNumber();
        tx.type = creationData.type();
        tx.status = TransactionStatus.PENDING;
        tx.amount = creationData.amount();
        tx.currency = creationData.currency();
        tx.externalReference = creationData.externalReference();
        tx.createdAt = LocalDateTime.now();
        tx.completedAt = null;
        return tx;
    }

    public static Transactions reconstitute(TransactionSnapshot snapshot) {
        Transactions tx = new Transactions();
        tx.id = snapshot.id();
        tx.userId = snapshot.userId();
        tx.referenceNumber = snapshot.referenceNumber();
        tx.type = snapshot.type();
        tx.status = snapshot.status();
        tx.amount = snapshot.amount();
        tx.currency = snapshot.currency();
        tx.externalReference = snapshot.externalReference();
        tx.failureReason = snapshot.failureReason();
        tx.createdAt = snapshot.createdAt();
        tx.completedAt = snapshot.completedAt();
        return tx;
    }

    public void markCreated(String externalReference) {
        if (status != TransactionStatus.PENDING) {
            return;
        }

        this.externalReference = externalReference;
    }

    public void markSucceeded() {
        if (status != TransactionStatus.PENDING) {
            return;
        }

        this.status = TransactionStatus.SUCCESS;
        this.completedAt = LocalDateTime.now();
    }

    public void markFailed(String reason) {
        if (status != TransactionStatus.PENDING) {
            return;
        }

        this.status = TransactionStatus.FAILED;
        this.failureReason = reason;
        this.completedAt = LocalDateTime.now();
    }

    private static String generateReferenceNumber() {
        StringBuilder sb = new StringBuilder("TXN-");
        for (int i = 0; i < 10; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdempotencyKey() {
        return idempotencyKey;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

}
