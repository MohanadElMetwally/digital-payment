package com.example.digital_payment.payment.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.PaymentSourceType;
import com.example.digital_payment.payment.domain.model.snapshots.PaymentSourceSnapshot;
import com.example.digital_payment.payment.domain.model.valueobjects.PaymentSourceCreationData;

public class PaymentSources {
    private UUID id;
    private UUID transactionId;
    private PaymentSourceType sourceType;
    private UUID sourceId;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public PaymentSources() {

    }

    public static PaymentSources create(PaymentSourceCreationData creationData) {
        PaymentSources ps = new PaymentSources();
        ps.id = UUID.randomUUID();
        ps.transactionId = creationData.transactionId();
        ps.sourceType = creationData.sourceType();
        ps.sourceId = creationData.sourceId();
        ps.amount = creationData.amount();
        ps.createdAt = LocalDateTime.now();
        return ps;
    }

    public static PaymentSources reconstitute(PaymentSourceSnapshot snapshot) {
        PaymentSources ps = new PaymentSources();
        ps.id = snapshot.id();
        ps.transactionId = snapshot.transactionId();
        ps.sourceType = snapshot.sourceType();
        ps.sourceId = snapshot.sourceId();
        ps.amount = snapshot.amount();
        ps.createdAt = snapshot.createdAt();
        return ps;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public PaymentSourceType getSourceType() {
        return sourceType;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
