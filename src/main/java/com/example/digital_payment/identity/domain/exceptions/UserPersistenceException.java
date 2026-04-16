package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class UserPersistenceException extends ConflictException {
    public UserPersistenceException(String message) {
        super("Failed to save user due to a conflict: " + message);
    }
}
