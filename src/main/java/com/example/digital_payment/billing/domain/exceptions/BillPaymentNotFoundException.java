package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class BillPaymentNotFoundException extends ResourceNotFoundException {
    public BillPaymentNotFoundException(String message) {
        super(message);
    }
}
