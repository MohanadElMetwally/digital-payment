package com.example.digital_payment.billing.domain.exceptions;

import java.util.UUID;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class BillerNotFoundException extends ResourceNotFoundException {
    public BillerNotFoundException(UUID id) {
        super("Biller with id: %s was not found".formatted(id));
    }
}
