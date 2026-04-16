package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class ProviderBillNotFound extends ResourceNotFoundException{
    public ProviderBillNotFound(String message) {
        super(message);
    }
}
