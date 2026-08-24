package com.example.digital_payment.payment.application.dto;

import java.util.UUID;

public record PaymentSucceededEvent(UUID transactionId, String externalReference,
        UUID referenceId) {

}
