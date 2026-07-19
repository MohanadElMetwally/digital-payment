package com.example.digital_payment.payment.application.dto;

import java.util.UUID;

public record PaymentFailedEvent(UUID transactionId, String failureReason, UUID referenceId) {

}
