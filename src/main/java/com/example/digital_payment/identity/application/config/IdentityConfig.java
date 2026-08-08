package com.example.digital_payment.identity.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.CheckUserExistsUseCase;
import com.example.digital_payment.identity.application.port.in.CheckUsersExistUseCase;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.in.LoginUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.identity.application.port.in.UpdatePasswordUseCase;
import com.example.digital_payment.identity.application.port.in.UpdateUserUseCase;
import com.example.digital_payment.identity.application.port.out.AuthPort;
import com.example.digital_payment.identity.application.port.out.CurrentUserPort;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.PasswordMatchPort;
import com.example.digital_payment.identity.application.port.out.PhoneValidatorPort;
import com.example.digital_payment.identity.application.port.out.ResolveCountryPort;
import com.example.digital_payment.identity.application.port.out.ResolveCurrencyPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.application.port.out.UpdatePasswordPort;
import com.example.digital_payment.identity.application.port.out.UpdateUserPort;
import com.example.digital_payment.identity.application.usecase.CheckUserExistsService;
import com.example.digital_payment.identity.application.usecase.CheckUsersExistService;
import com.example.digital_payment.identity.application.usecase.GetCurrentUserService;
import com.example.digital_payment.identity.application.usecase.GetUserService;
import com.example.digital_payment.identity.application.usecase.LoginService;
import com.example.digital_payment.identity.application.usecase.RegisterUserService;
import com.example.digital_payment.identity.application.usecase.UpdatePasswordService;
import com.example.digital_payment.identity.application.usecase.UpdateUserService;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class IdentityConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(SaveUserPort saveUserPort,
        ResolveCountryPort resolveCountryPort, ResolveCurrencyPort resolveCurrencyPort,
        EventPublisherPort eventPublisherPort, TransactionPort transactionPort,
        PhoneValidatorPort phoneValidatorPort, UserMapper userMapper) {
        return new RegisterUserService(saveUserPort, resolveCountryPort, resolveCurrencyPort,
            eventPublisherPort, transactionPort, phoneValidatorPort, userMapper);
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

    @Bean
    public CheckUsersExistUseCase checkUsersExistUseCase(LoadUserPort loadUserPort) {
        return new CheckUsersExistService(loadUserPort);
    }

    @Bean
    public GetCurrentUserService getCurrentUserService(CurrentUserPort currentUserPort,
        UserMapper userMapper) {
        return new GetCurrentUserService(currentUserPort, userMapper);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(LoadUserPort loadUserPort,
        UpdateUserPort updateUserPort, TransactionPort transactionPort, UserMapper userMapper) {
        return new UpdateUserService(loadUserPort, updateUserPort, transactionPort, userMapper);
    }

    @Bean
    public UpdatePasswordUseCase updatePasswordUseCase(LoadUserPort loadUserPort,
        SaveUserPort saveUserPort, PasswordMatchPort passwordMatchPort,
        UpdatePasswordPort updateUserPort, TransactionPort transactionPort) {
        return new UpdatePasswordService(loadUserPort, saveUserPort, passwordMatchPort,
            updateUserPort, transactionPort);
    }

    @Bean
    public CheckUserExistsUseCase checkUserExistsUseCase(LoadUserPort loadUserPort) {
        return new CheckUserExistsService(loadUserPort);
    }
}
