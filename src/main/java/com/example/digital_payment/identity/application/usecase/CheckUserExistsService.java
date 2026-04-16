package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.port.in.CheckUserExistsUseCase;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.domain.exceptions.EmailAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.PhoneAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.UsernameAlreadyExistsException;

public class CheckUserExistsService implements CheckUserExistsUseCase {
    private final LoadUserPort loadUserPort;

    public CheckUserExistsService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public void checkNotExists(RegisterUserCommand command) {
        loadUserPort
            .findByEmailOrUsernameOrPhone(command.email(), command.username(), command.phone())
            .ifPresent(exists -> {
                if (exists.getEmail().equals(command.email())) {
                    throw new EmailAlreadyExistsException(command.email());
                }
                if (exists.getUsername().equals(command.username())) {
                    throw new UsernameAlreadyExistsException(command.username());
                }
                if (exists.getPhone().equals(command.phone())) {
                    throw new PhoneAlreadyExistsException(command.phone());
                }
            });
    }
}
