package com.example.digital_payment.payment.api.mapper;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.api.dto.request.RegisterCreditCardRequest;
import com.example.digital_payment.payment.api.dto.response.CreditCardResponse;
import com.example.digital_payment.payment.application.dto.RegisterCreditCardCommand;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

@Component
public class CreditCardApiMapper {
    public RegisterCreditCardCommand toRegisterCreditCardCommand(UUID userId,
            RegisterCreditCardRequest request) {
        return new RegisterCreditCardCommand(userId, request.brand(), request.lastFour(),
                request.expiryMonth(), request.expiryYear(), request.paymentMethodId(),
                request.status(), request.isDefault());
    }

    public CreditCardResponse toResponse(CreditCards card) {
        return new CreditCardResponse(card.getLastFour(), card.getExpiryMonth(),
                card.getExpiryYear(), card.getBrand().toString(), card.getStatus(),
                card.isDefault());
    }
}
