package com.example.digital_payment.billing.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.billing.domain.enums.BillPaymentStatus;
import com.example.digital_payment.billing.domain.model.snapshots.BillPaymentSnapshot;
import com.example.digital_payment.billing.domain.model.valueobjects.BillPaymentCreationData;

public class BillPayments {
    private UUID id;
    private UUID transactionId;
    private UUID billId;
    private BigDecimal amount;
    private BillPaymentStatus status;
    private LocalDateTime paidAt;

    public BillPayments() {

    }

    public static BillPayments create(BillPaymentCreationData createData) {
        BillPayments billPayment = new BillPayments();
        billPayment.id = UUID.randomUUID();
        billPayment.transactionId = createData.transactionId();
        billPayment.billId = createData.billId();
        billPayment.amount = createData.amount();
        billPayment.status = BillPaymentStatus.PENDING;
        billPayment.paidAt = null;
        return billPayment;
    }

    public static BillPayments reconstitute(BillPaymentSnapshot snapshot) {
        BillPayments billPayment = new BillPayments();
        billPayment.id = snapshot.id();
        billPayment.transactionId = snapshot.transactionId();
        billPayment.billId = snapshot.billId();
        billPayment.amount = snapshot.amount();
        billPayment.status = snapshot.status();
        billPayment.paidAt = snapshot.paidAt();
        return billPayment;
    }

    public void markSucceeded() {
        if (status != BillPaymentStatus.PENDING) {
            return;
        }

        this.status = BillPaymentStatus.SUCCESS;
        this.paidAt = LocalDateTime.now();
    }

    public void markFailed() {
        if (status != BillPaymentStatus.PENDING) {
            return;
        }

        this.status = BillPaymentStatus.FAILED;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getBillId() {
        return billId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BillPaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}