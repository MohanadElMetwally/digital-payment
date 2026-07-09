package com.example.digital_payment.notification.application.port.in;

import com.example.digital_payment.notification.application.dto.CreateNotificationCommand;

public interface GreetNotificationUserUseCase {
    void handle(CreateNotificationCommand command);
}
