package com.example.digital_payment.identity.api.dto.response;

import com.example.digital_payment.identity.application.dto.TokenResult;

public record TokenResponse(String accessToken, String tokenType) {
    public static TokenResponse from(TokenResult result) {
        return new TokenResponse(result.accessToken(), result.tokenType());
    }
}
