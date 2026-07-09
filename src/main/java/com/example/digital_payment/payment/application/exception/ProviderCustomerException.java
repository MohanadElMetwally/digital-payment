package com.example.digital_payment.payment.application.exception;

public class ProviderCustomerException extends RuntimeException {
    public ProviderCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
