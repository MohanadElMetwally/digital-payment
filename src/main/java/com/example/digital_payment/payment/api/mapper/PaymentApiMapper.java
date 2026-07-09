package com.example.digital_payment.payment.api.mapper;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.api.dto.response.PaymentResponse;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

@Component
public class PaymentApiMapper {
    public PaymentResponse toResponse(Transactions transaction) {
        return new PaymentResponse(transaction.getReferenceNumber(),
            transaction.getType().toString(), transaction.getStatus().toString(),
            transaction.getAmount(), transaction.getCurrency());
    }

    public InitiatePaymentCommand toInitiatePaymentCommand(UUID userId, UUID idempotencyKey,
        UUID billId, UUID creditCardId, String currency, BigDecimal amount) {
        return new InitiatePaymentCommand(userId, idempotencyKey, billId, creditCardId, currency,
            amount);
    }
}
