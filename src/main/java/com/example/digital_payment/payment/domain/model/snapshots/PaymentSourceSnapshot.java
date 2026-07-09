package com.example.digital_payment.payment.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.PaymentSourceType;

public record PaymentSourceSnapshot(UUID id, UUID transactionId, PaymentSourceType sourceType,
    UUID sourceId, BigDecimal amount, LocalDateTime createdAt) {
}
