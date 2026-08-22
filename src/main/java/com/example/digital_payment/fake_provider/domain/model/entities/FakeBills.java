package com.example.digital_payment.fake_provider.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.fake_provider.domain.enums.Provider;
import com.example.digital_payment.fake_provider.domain.enums.ProviderBillStatus;
import com.example.digital_payment.fake_provider.domain.model.snapshot.FakeBillSnapshot;

public class FakeBills {
    private UUID id;
    private String customerNumber;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    private Provider provider;
    private ProviderBillStatus status;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    public FakeBills() {

    }

    @Override
    public String toString() {
        return "FakeBills [id=" + id + ", customerNumber=" + customerNumber + ", customerName="
                + customerName + ", amount=" + amount + ", currency=" + currency + ", status="
                + status + ", billingPeriodStart=" + billingPeriodStart + ", billingPeriodEnd="
                + billingPeriodEnd + ", dueDate=" + dueDate + ", createdAt=" + createdAt + "]";
    }

    public static FakeBills reconstitute(FakeBillSnapshot snapshot) {
        FakeBills fakeBill = new FakeBills();
        fakeBill.id = snapshot.id();
        fakeBill.customerNumber = snapshot.customerNumber();
        fakeBill.customerName = snapshot.customerName();
        fakeBill.amount = snapshot.amount();
        fakeBill.currency = snapshot.currency();
        fakeBill.provider = snapshot.provider();
        fakeBill.status = snapshot.status();
        fakeBill.billingPeriodStart = snapshot.billingPeriodStart();
        fakeBill.billingPeriodEnd = snapshot.billingPeriodEnd();
        fakeBill.dueDate = snapshot.billingPeriodEnd();
        fakeBill.createdAt = snapshot.createdAt();
        return fakeBill;
    }

    public void markPaid() {
        this.status = ProviderBillStatus.PAID;
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public ProviderBillStatus getStatus() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Provider getProvider() {
        return provider;
    }
}
