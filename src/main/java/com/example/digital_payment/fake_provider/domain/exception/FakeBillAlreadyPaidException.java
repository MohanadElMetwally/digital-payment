package com.example.digital_payment.fake_provider.domain.exception;

import com.example.digital_payment.shared.exception.ConflictException;

public class FakeBillAlreadyPaidException extends ConflictException {
    public FakeBillAlreadyPaidException(String message) {
        super(message);
    }
}
