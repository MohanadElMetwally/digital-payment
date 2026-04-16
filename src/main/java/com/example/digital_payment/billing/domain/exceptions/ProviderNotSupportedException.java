package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.UnprocessableEntityException;

public class ProviderNotSupportedException extends UnprocessableEntityException {
    public ProviderNotSupportedException(String message) {
        super(message);
    }
}
