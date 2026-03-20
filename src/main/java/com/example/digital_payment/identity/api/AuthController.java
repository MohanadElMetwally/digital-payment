package com.example.digital_payment.identity.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.identity.api.dto.response.TokenResponse;
import com.example.digital_payment.identity.application.port.in.LoginUseCase;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenResponse> login(@RequestParam String username,
        @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(TokenResponse.from(loginUseCase.verify(username, password)));
    }
}
