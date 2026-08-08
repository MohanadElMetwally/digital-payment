package com.example.digital_payment.payment.infrastructure.exception;

public class InvalidWebhookSignatureException extends RuntimeException {
public InvalidWebhookSignatureException(Throwable cause) {
        super("Could not validate webhook signature", cause);
    }
}
