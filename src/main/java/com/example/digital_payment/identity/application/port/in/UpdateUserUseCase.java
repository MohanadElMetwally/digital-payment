package com.example.digital_payment.identity.application.port.in;

import java.util.UUID;
import com.example.digital_payment.identity.application.dto.UpdateUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;

public interface UpdateUserUseCase {
    UserResult updateById(UUID id, UpdateUserCommand command);

    UserResult updateMe(UUID id, UpdateUserCommand command);
}
