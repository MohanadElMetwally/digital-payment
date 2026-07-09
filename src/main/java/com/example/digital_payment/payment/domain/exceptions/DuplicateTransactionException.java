package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class DuplicateTransactionException extends ConflictException {
    public DuplicateTransactionException(String message) {
        super(message);
    }
}
