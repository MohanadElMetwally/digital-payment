package com.example.digital_payment.payment.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand(UUID transactionId, String customerId, BigDecimal amount,
    String currency, String paymentMethodToken, UUID idempotencyKey) {

}
