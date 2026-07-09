package com.example.digital_payment.payment.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InitiatePaymentCommand(UUID userId, UUID idempotencyKey, UUID billId,
    UUID creditCardId, String currency, BigDecimal amount) {

}
