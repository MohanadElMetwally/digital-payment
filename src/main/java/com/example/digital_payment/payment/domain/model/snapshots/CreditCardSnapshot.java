package com.example.digital_payment.payment.domain.model.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.CardBrand;
import com.example.digital_payment.payment.domain.enums.CardStatus;

public record CreditCardSnapshot(UUID id, UUID userId, CardBrand brand, String lastFour,
    String expiryMonth, String expiryYear, String paymentMethodId, CardStatus status, Boolean isDefault,
    LocalDateTime createdAt) {
}
