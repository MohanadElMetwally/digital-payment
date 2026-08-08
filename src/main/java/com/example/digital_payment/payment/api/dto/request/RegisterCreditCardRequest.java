package com.example.digital_payment.payment.api.dto.request;

import com.example.digital_payment.payment.domain.enums.CardBrand;
import com.example.digital_payment.payment.domain.enums.CardStatus;

import jakarta.validation.constraints.NotNull;

public record RegisterCreditCardRequest(@NotNull CardBrand brand, @NotNull String lastFour,
    @NotNull String expiryMonth, @NotNull String expiryYear, @NotNull String paymentMethodId,
    @NotNull CardStatus status, @NotNull Boolean isDefault) {

}
