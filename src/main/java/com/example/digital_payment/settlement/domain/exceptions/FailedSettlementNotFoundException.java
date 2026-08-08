package com.example.digital_payment.settlement.domain.exceptions;

import java.util.UUID;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class FailedSettlementNotFoundException extends ResourceNotFoundException {
    public FailedSettlementNotFoundException(UUID id) {
        super("Failed settlement not found for ID: %s.".formatted(id));
    }
}