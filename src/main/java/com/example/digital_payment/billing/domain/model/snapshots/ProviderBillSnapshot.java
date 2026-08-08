package com.example.digital_payment.billing.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import com.example.digital_payment.billing.domain.enums.BillStatus;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;

public record ProviderBillSnapshot(String id, String customerNumber, String customerName,
    BigDecimal amount, String currency, ServiceProvider provider, BillStatus status,
    LocalDate billingPeriodStart, LocalDate billingPeriodEnd, LocalDate dueDate,
    LocalDateTime createAt) {

}
