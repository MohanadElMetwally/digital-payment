package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordAdapter {

    private final PasswordEncoder encoder;

    public BCryptPasswordAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
