package com.example.digital_payment.identity.domain.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException {
    public PhoneAlreadyExistsException(String phone){
        super(phone + " Already exists.");
    }
}
