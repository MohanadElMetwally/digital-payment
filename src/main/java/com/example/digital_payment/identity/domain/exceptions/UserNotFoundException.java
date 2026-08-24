package com.example.digital_payment.identity.domain.exceptions;

import java.util.UUID;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(UUID id) {
        super("User with id: %s was not found".formatted(id));
    }
}
