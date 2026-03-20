package com.example.digital_payment.identity.domain.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super(email + " Already Exists.");
    }
}
