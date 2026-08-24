package com.example.digital_payment.payment.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionType;

public record TransactionCreationData(UUID userId, UUID idempotencyKey, TransactionType type,
        BigDecimal amount, String currency, String externalReference) {
}
