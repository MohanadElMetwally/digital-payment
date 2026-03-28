package com.example.digital_payment.identity.domain.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password. Please check your password and try again.");
    }
}
