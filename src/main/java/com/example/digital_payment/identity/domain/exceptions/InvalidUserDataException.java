package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.BadRequestException;

public class InvalidUserDataException extends BadRequestException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
