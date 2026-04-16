package com.example.digital_payment.fake_provider.domain.exception;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class FakeBillNotFoundException extends ResourceNotFoundException{
    public FakeBillNotFoundException(String message) {
        super(message);
    }
}
