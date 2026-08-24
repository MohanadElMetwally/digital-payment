package com.example.digital_payment.payment.application.dto;

import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.CardBrand;
import com.example.digital_payment.payment.domain.enums.CardStatus;

public record RegisterCreditCardCommand(UUID userId, CardBrand brand, String lastFour,
        String expiryMonth, String expiryYear, String paymentMethodId, CardStatus status,
        Boolean isDefault) {

}
