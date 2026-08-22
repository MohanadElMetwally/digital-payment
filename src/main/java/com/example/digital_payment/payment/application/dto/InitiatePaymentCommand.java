package com.example.digital_payment.payment.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionType;

public record InitiatePaymentCommand(UUID userId, UUID idempotencyKey, UUID creditCardId,
        TransactionType type, String currency, BigDecimal amount, UUID referenceId,
        boolean useWallet, UUID walletId) {
    public InitiatePaymentCommand(UUID userId, UUID idempotencyKey, UUID creditCardId,
            TransactionType type, String currency, BigDecimal amount, UUID referenceId) {
        this(userId, idempotencyKey, creditCardId, type, currency, amount, referenceId, false,
                null);
    }
}
