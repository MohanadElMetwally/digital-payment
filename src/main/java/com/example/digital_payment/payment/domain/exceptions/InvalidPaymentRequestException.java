package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class InvalidPaymentRequestException extends ResourceNotFoundException {
    public InvalidPaymentRequestException(String message) {
        super(message);
    }
}
