package com.example.digital_payment.payment.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.api.dto.response.PaymentResponse;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

@Component
public class PaymentApiMapper {
    public PaymentResponse toResponse(Transactions transaction) {
        return new PaymentResponse(transaction.getReferenceNumber(),
            transaction.getType().toString(), transaction.getStatus().toString(),
            transaction.getAmount(), transaction.getCurrency());
    }
}
