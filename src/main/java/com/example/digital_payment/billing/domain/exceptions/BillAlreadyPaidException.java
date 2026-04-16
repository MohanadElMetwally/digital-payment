package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class BillAlreadyPaidException extends ConflictException {
    public BillAlreadyPaidException(String message) {
        super(message);
    }
}
