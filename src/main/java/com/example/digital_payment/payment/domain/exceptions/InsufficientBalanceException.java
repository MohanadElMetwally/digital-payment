package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.UnprocessableEntityException;

public class InsufficientBalanceException extends UnprocessableEntityException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
