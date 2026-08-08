package com.example.digital_payment.identity.application.port.in;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;

public interface RegisterUserUseCase {
    UserResult register(RegisterUserCommand command);
}
