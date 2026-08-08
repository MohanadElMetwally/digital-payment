package com.example.digital_payment.identity.application.port.in;

import com.example.digital_payment.identity.application.dto.TokenResult;

public interface LoginUseCase {
    TokenResult verify(String username, String password);
}
