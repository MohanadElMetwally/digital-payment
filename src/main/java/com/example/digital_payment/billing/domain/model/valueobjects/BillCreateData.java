package com.example.digital_payment.billing.domain.model.valueobjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BillCreateData(UUID billerId, UUID userId, String externalCustomerNumber,
    String externalCustomerName, String externalBillId, BigDecimal amount, String currency,
    LocalDate billingPeriodStart, LocalDate billingPeriodEnd, LocalDate dueDate) {

}
