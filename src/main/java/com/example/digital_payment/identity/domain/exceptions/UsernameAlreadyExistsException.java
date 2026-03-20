package com.example.digital_payment.identity.domain.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super(username + " Already Exists.");
    }
}
