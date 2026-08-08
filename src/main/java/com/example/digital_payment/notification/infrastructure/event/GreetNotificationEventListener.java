package com.example.digital_payment.notification.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.notification.application.dto.CreateNotificationCommand;
import com.example.digital_payment.notification.application.port.in.GreetNotificationUserUseCase;
import com.example.digital_payment.notification.domain.constants.NotificationMessages;
import com.example.digital_payment.notification.domain.enums.NotificationType;
import com.example.digital_payment.shared.events.UserRegisteredEvent;

@Component
public class GreetNotificationEventListener {
    private final GreetNotificationUserUseCase greetNotificationUserCase;

    public GreetNotificationEventListener(GreetNotificationUserUseCase greetNotificationUserCase) {
        this.greetNotificationUserCase = greetNotificationUserCase;
    }

    @ApplicationModuleListener
    public void on(UserRegisteredEvent event) {
        greetNotificationUserCase.handle(
            new CreateNotificationCommand(event.userId(), NotificationMessages.WELCOME_TITLE,
                NotificationMessages.WELCOME_MESSAGE, NotificationType.SYSTEM));
    }
}
