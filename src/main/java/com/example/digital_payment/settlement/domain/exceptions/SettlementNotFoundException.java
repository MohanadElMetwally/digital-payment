package com.example.digital_payment.settlement.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class SettlementNotFoundException extends ResourceNotFoundException {
    public SettlementNotFoundException() {
        super("Settlement not found");
    }
}