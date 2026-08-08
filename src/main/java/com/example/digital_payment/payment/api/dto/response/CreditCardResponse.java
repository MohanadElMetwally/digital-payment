package com.example.digital_payment.payment.api.dto.response;

import com.example.digital_payment.payment.domain.enums.CardStatus;

public record CreditCardResponse(String lastFour, String expiryMonth, String expiryYear,
    String brand, CardStatus status, Boolean isDefault) {

}
