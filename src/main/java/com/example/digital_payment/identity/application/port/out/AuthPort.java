package com.example.digital_payment.identity.application.port.out;

import com.example.digital_payment.identity.application.dto.TokenResult;

public interface AuthPort {
    TokenResult verify(String username, String password);
}
