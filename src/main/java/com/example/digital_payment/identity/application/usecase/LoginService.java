package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.dto.TokenResult;
import com.example.digital_payment.identity.application.port.in.LoginUseCase;
import com.example.digital_payment.identity.application.port.out.AuthPort;

public class LoginService implements LoginUseCase {
    private final AuthPort authPort;

    public LoginService(AuthPort authPort) {
        this.authPort = authPort;
    }

    @Override
    public TokenResult verify(String username, String password) {
        return authPort.verify(username, password);
    }

}
