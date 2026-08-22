package com.example.digital_payment.billing.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillStatus;
import com.example.digital_payment.billing.domain.model.snapshots.BillSnapshot;
import com.example.digital_payment.billing.domain.model.valueobjects.BillCreateData;

public class Bills {
    private UUID id;
    private UUID billerId;
    private UUID userId;
    private String externalCustomerNumber;
    private String externalCustomerName;
    private String externalBillId;
    private BigDecimal amount;
    private String currency;
    private BillStatus status;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private LocalDateTime lastSyncedAt;
    private LocalDateTime createdAt;

    public Bills() {}

    public static Bills create(BillCreateData createData) {
        Bills bill = new Bills();
        bill.id = UUID.randomUUID();
        bill.billerId = createData.billerId();
        bill.userId = createData.userId();
        bill.externalCustomerNumber = createData.externalCustomerNumber();
        bill.externalCustomerName = createData.externalCustomerName();
        bill.externalBillId = createData.externalBillId();
        bill.amount = createData.amount();
        bill.currency = createData.currency();
        bill.status = BillStatus.UNPAID;
        bill.billingPeriodStart = createData.billingPeriodStart();
        bill.billingPeriodEnd = createData.billingPeriodEnd();
        bill.dueDate = createData.dueDate();
        bill.lastSyncedAt = LocalDateTime.now();
        bill.createdAt = LocalDateTime.now();
        return bill;
    }

    public static Bills reconstitute(BillSnapshot snapshot) {
        Bills bill = new Bills();
        bill.id = snapshot.id();
        bill.billerId = snapshot.billerId();
        bill.userId = snapshot.userId();
        bill.externalBillId = snapshot.externalBillId();
        bill.externalCustomerName = snapshot.externalCustomerName();
        bill.externalCustomerNumber = snapshot.externalCustomerNumber();
        bill.amount = snapshot.amount();
        bill.currency = snapshot.currency();
        bill.status = snapshot.status();
        bill.billingPeriodStart = snapshot.billingPeriodStart();
        bill.billingPeriodEnd = snapshot.billingPeriodEnd();
        bill.dueDate = snapshot.dueDate();
        bill.lastSyncedAt = snapshot.lastSyncedAt();
        bill.createdAt = snapshot.createdAt();
        return bill;
    }

    public boolean syncFromProvider(BigDecimal latestAmount) {
        if (this.status == BillStatus.PENDING || this.status == BillStatus.PAID) {
            throw new IllegalStateException(
                    "Cannot sync bill, it is already in status %s".formatted(status));
        }
        boolean amountChanged = this.amount.compareTo(latestAmount) != 0;
        this.amount = latestAmount;
        this.lastSyncedAt = LocalDateTime.now();
        return amountChanged;
    }

    public void markPending() {
        if (this.status != BillStatus.UNPAID) {
            return;
        }
        this.status = BillStatus.PENDING;
    }

    public void markPaid() {
        if (this.status != BillStatus.PENDING) {
            return;
        }
        this.status = BillStatus.PAID;
    }

    public void markUnpaid() {
        if (this.status != BillStatus.PENDING) {
            return;
        }
        this.status = BillStatus.UNPAID;
    }

    public boolean isResyncable() {
        return this.status == BillStatus.UNPAID;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBillerId() {
        return billerId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getExternalCustomerNumber() {
        return externalCustomerNumber;
    }

    public String getExternalBillId() {
        return externalBillId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BillStatus getStatus() {
        return status;
    }

    public LocalDate getBillingPeriodStart() {
        return billingPeriodStart;
    }

    public LocalDate getBillingPeriodEnd() {
        return billingPeriodEnd;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDateTime getLastSyncedAt() {
        return lastSyncedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getExternalCustomerName() {
        return externalCustomerName;
    }
}
