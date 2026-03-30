package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.dto.TokenResult;
import com.example.digital_payment.identity.application.port.out.AuthPort;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.infrastructure.security.UserPrincipal;
import com.example.digital_payment.shared.security.jwt.JWTService;

@Component
public class AuthAdapter implements AuthPort {
    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public AuthAdapter(AuthenticationManager authManager, JWTService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResult verify(String username, String password) {

        Authentication authentication = authManager
            .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Users user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        return new TokenResult(jwtService.generateToken(user.getId(), user.getUsername()));
    }

}
