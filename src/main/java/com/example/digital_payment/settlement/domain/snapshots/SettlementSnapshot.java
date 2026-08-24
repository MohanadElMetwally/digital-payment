package com.example.digital_payment.settlement.domain.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.enums.SettlementStatus;

public record SettlementSnapshot(UUID id, UUID billId, UUID userId, String customerNumber,
        BigDecimal amount, String currency, SettlementStatus status, UUID providerIdempotencyKey,
        String providerReference, int attemptCount, LocalDateTime lastAttemptedAt, String lastError,
        LocalDateTime processedAt, LocalDateTime createdAt) {
}
