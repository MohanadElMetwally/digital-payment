package com.example.digital_payment.billing.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import com.example.digital_payment.billing.domain.enums.BillStatus;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;
import com.example.digital_payment.billing.domain.model.snapshots.ProviderBillSnapshot;

public class ProviderBills {
    private String id;
    private String customerNumber;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    private ServiceProvider provider;
    private BillStatus status;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private LocalDateTime createAt;

    public ProviderBills() {

    }

    public static ProviderBills reconstitute(ProviderBillSnapshot snapshot) {
        ProviderBills providerBill = new ProviderBills();
        providerBill.id = snapshot.id();
        providerBill.customerNumber = snapshot.customerNumber();
        providerBill.customerName = snapshot.customerName();
        providerBill.amount = snapshot.amount();
        providerBill.currency = snapshot.currency();
        providerBill.provider = snapshot.provider();
        providerBill.status = snapshot.status();
        providerBill.billingPeriodStart = snapshot.billingPeriodStart();
        providerBill.billingPeriodEnd = snapshot.billingPeriodEnd();
        providerBill.dueDate = snapshot.dueDate();
        providerBill.createAt = snapshot.createAt();
        return providerBill;
    }

    public ServiceProvider getProvider() {
        return provider;
    }

    public String getId() {
        return id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public String toString() {
        return "ProviderBills [id=" + id + ", customerNumber=" + customerNumber + ", customerName="
            + customerName + ", amount=" + amount + ", currency=" + currency + ", status=" + status
            + ", billingPeriodStart=" + billingPeriodStart + ", billingPeriodEnd="
            + billingPeriodEnd + ", dueDate=" + dueDate + ", createAt=" + createAt + "]";
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getCustomerName() {
        return customerName;
    }

}
