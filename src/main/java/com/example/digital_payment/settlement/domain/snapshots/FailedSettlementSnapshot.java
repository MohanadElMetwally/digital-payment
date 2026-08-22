package com.example.digital_payment.settlement.domain.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.enums.FailedSettlementResolutionStatus;

public record FailedSettlementSnapshot(UUID id, UUID settlementId, UUID billId, UUID userId,
        String customerNumber, BigDecimal amount, String currency, String failureReason,
        int attemptCount, FailedSettlementResolutionStatus resolutionStatus, String resolutionNotes,
        String resolvedBy, LocalDateTime resolvedAt, LocalDateTime createdAt) {

}
