package com.example.digital_payment.identity.domain.exceptions;

import com.example.digital_payment.shared.exception.ConflictException;

public class PhoneAlreadyExistsException extends ConflictException {
    public PhoneAlreadyExistsException(String phone) {
        super(phone + " Already exists.");
    }
}
