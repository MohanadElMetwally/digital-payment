package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.digital_payment.identity.application.port.out.PasswordMatchPort;

@Component
public class PasswordMatchAdapter implements PasswordMatchPort {
    private final PasswordEncoder encoder;

    public PasswordMatchAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}
