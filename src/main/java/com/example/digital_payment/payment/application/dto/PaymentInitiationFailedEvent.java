package com.example.digital_payment.payment.application.dto;

import java.util.UUID;

public record PaymentInitiationFailedEvent(UUID transactionId, String failureReason) {

}
