package com.example.digital_payment.payment.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;
import com.example.digital_payment.payment.domain.enums.TransactionType;

public record TransactionSnapshot(UUID id, UUID userId, UUID idempotencyKey, String referenceNumber,
        TransactionType type, TransactionStatus status, BigDecimal amount, String currency,
        String externalReference, String failureReason, LocalDateTime createdAt,
        LocalDateTime completedAt) {
}
