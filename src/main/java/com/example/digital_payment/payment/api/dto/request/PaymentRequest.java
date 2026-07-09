package com.example.digital_payment.payment.api.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(@NotNull UUID billId, @NotNull UUID creditCardId) {

}
