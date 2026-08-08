package com.example.digital_payment.settlement.domain.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;

public record FailedSettlementCreationData(UUID settlementId, UUID billId, UUID userId,
    String customerNumber, BigDecimal amount, String currency, String failureReason,
    int attemptCount) {
}