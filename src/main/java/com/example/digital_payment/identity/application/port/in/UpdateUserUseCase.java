package com.example.digital_payment.identity.application.port.in;

import com.example.digital_payment.identity.application.dto.UpdateUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;

public interface UpdateUserUseCase {
    UserResult update(UpdateUserCommand command);
}
