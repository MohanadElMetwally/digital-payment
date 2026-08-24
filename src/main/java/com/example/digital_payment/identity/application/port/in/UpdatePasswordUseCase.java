package com.example.digital_payment.identity.application.port.in;

import java.util.UUID;
import com.example.digital_payment.identity.application.dto.UpdatePasswordCommand;

public interface UpdatePasswordUseCase {
    void updatePassword(UUID id, UpdatePasswordCommand command);
}
