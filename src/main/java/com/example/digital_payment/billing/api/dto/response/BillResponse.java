package com.example.digital_payment.billing.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record BillResponse(UUID id, UUID billerId, UUID userId, String externalCustomerNumber,
    String externalCustomerName, String externalBillId, BigDecimal amount, String currency,
    String status, String billingPeriodStart, String billingPeriodEnd, String dueDate,
    String lastSyncedAt, String createdAt) {

}
