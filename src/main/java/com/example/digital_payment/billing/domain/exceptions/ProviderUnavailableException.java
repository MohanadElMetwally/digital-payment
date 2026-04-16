package com.example.digital_payment.billing.domain.exceptions;

import com.example.digital_payment.shared.exception.ServiceUnavailableException;

public class ProviderUnavailableException extends ServiceUnavailableException {
    public ProviderUnavailableException(String message) {
        super(message);
    }
}
