package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException() {
        super("Invalid password. Please check your password and try again.");
    }
}
