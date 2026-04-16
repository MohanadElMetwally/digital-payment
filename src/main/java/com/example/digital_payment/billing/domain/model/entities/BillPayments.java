package com.example.digital_payment.billing.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.billing.domain.enums.BillPaymentStatus;
import com.example.digital_payment.billing.domain.model.snapshots.BillPaymentSnapshot;
import com.example.digital_payment.billing.domain.model.valueobjects.BillPaymentCreateData;

public class BillPayments {
    private UUID id;
    private UUID transaction_id;
    private UUID bill_id;
    private BigDecimal amount;
    private BillPaymentStatus status;
    private LocalDateTime paid_at;

    public BillPayments() {

    }

    public BillPayments create(BillPaymentCreateData createData) {
        BillPayments billPayment = new BillPayments();
        billPayment.id = UUID.randomUUID();
        billPayment.transaction_id = createData.transaction_id();
        billPayment.bill_id = createData.bill_id();
        billPayment.amount = createData.amount();
        billPayment.status = BillPaymentStatus.PENDING;
        billPayment.paid_at = null;
        return billPayment;
    }

    public BillPayments reconstitute(BillPaymentSnapshot snapshot){
        BillPayments billPayment = new BillPayments();
        billPayment.id = snapshot.id();
        billPayment.transaction_id = snapshot.transaction_id();
        billPayment.bill_id = snapshot.bill_id();
        billPayment.amount = snapshot.amount();
        billPayment.status = snapshot.status();
        billPayment.paid_at = snapshot.paid_at();
        return billPayment;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public UUID getBill_id() {
        return bill_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BillPaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaid_at() {
        return paid_at;
    }
}