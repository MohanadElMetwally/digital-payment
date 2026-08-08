package com.example.digital_payment.payment.application.dto;

import java.util.UUID;

public record PaymentInitiationResult(UUID transactionId, String externalReference) {

}
