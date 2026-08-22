package com.example.digital_payment.billing.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillStatus;

public record BillResult(UUID id, UUID billerId, UUID userId, String externalCustomerNumber,
        String externalCustomerName, String externalBillId, BigDecimal amount, String currency,
        BillStatus status, LocalDate billingPeriodStart, LocalDate billingPeriodEnd,
        LocalDate dueDate, LocalDateTime lastSyncedAt, LocalDateTime createdAt) {

}
