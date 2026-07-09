package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class PaymentCustomerNotFoundException extends ResourceNotFoundException {
    public PaymentCustomerNotFoundException(String message) {
        super(message);
    }
}
