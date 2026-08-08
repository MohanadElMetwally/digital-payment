package com.example.digital_payment.billing.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.billing.domain.enums.BillStatus;

public record BillSnapshot(UUID id, UUID billerId, UUID userId, String externalCustomerNumber,
    String externalCustomerName, String externalBillId, BigDecimal amount, String currency,
    BillStatus status, LocalDate billingPeriodStart, LocalDate billingPeriodEnd, LocalDate dueDate,
    LocalDateTime lastSyncedAt, LocalDateTime createdAt) {

}
