package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class UsernameAlreadyExistsException extends ConflictException {
    public UsernameAlreadyExistsException(String username) {
        super(username + " Already Exists.");
    }
}
