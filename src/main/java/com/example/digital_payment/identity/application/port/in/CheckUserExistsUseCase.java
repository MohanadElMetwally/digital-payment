package com.example.digital_payment.identity.application.port.in;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;

public interface CheckUserExistsUseCase {
    void checkNotExists(RegisterUserCommand command);
}
