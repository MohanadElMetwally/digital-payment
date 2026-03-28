package com.example.digital_payment.identity.application.port.out;

public interface PasswordMatchPort {
    boolean matches(String rawPassword, String encodedPassword);
}
