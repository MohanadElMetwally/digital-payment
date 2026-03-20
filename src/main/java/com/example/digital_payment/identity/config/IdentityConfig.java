package com.example.digital_payment.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.in.LoginUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.identity.application.port.out.AuthPort;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.application.usecase.GetUserService;
import com.example.digital_payment.identity.application.usecase.LoginService;
import com.example.digital_payment.identity.application.usecase.RegisterUserService;

@Configuration
public class IdentityConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(LoadUserPort loadUserPort,
        SaveUserPort saveUserPort, UserMapper userMapper) {
        return new RegisterUserService(loadUserPort, saveUserPort, userMapper);
    }

    @Bean
    public GetUserUseCase getUserUseCase(LoadUserPort loadUserPort, UserMapper userMapper) {
        return new GetUserService(loadUserPort, userMapper);
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public LoginUseCase loginUseCase(AuthPort authPort) {
        return new LoginService(authPort);
    }

}
