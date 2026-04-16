package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class BillNotFoundException extends ResourceNotFoundException {
    public BillNotFoundException(String message) {
        super("Bill not found");
    }
}
