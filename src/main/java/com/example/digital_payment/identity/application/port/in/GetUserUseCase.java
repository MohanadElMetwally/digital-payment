package com.example.digital_payment.identity.application.port.in;

import java.util.UUID;
import com.example.digital_payment.identity.application.dto.UserResult;

public interface GetUserUseCase {
    UserResult getById(UUID userId);
}
