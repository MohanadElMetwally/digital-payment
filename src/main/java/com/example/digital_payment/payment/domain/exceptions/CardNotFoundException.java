package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class CardNotFoundException extends ResourceNotFoundException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
