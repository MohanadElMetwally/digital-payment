package com.example.digital_payment.shared.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class SecurityCurrentUserContext implements CurrentUserContext {
    @Override
    public UUID getUserId() {
        Claims claims = extractClaims();
        return UUID.fromString(claims.get("userId", String.class));
    }

    private Claims extractClaims() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getCredentials() instanceof Claims)) {
            throw new IllegalStateException("No JWT claims found in security context");
        }
        return (Claims) auth.getCredentials();
    }
}