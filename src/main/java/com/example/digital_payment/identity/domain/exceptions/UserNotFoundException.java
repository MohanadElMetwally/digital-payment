package com.example.digital_payment.identity.domain.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User with id: %s was not found".formatted(id));
    }
}
