package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException(String email) {
        super(email + " Already Exists.");
    }
}
