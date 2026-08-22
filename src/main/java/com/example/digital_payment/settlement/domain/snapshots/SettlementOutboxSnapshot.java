package com.example.digital_payment.settlement.domain.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.enums.OutboxStatus;

public record SettlementOutboxSnapshot(UUID id, UUID settlementId, OutboxStatus status,
        int attemptCount, String lastError, LocalDateTime createdAt, LocalDateTime publishedAt) {
}
