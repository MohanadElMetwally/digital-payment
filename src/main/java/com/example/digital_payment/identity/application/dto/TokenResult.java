package com.example.digital_payment.identity.application.dto;

public record TokenResult(String accessToken, String tokenType) {
    public TokenResult(String accessToken) {
        this(accessToken, "bearer");
    }
}
