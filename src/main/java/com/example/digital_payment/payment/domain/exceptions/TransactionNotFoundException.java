package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class TransactionNotFoundException extends ResourceNotFoundException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
