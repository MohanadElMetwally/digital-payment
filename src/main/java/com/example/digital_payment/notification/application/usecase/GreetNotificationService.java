package com.example.digital_payment.notification.application.usecase;

import com.example.digital_payment.notification.application.dto.CreateNotificationCommand;
import com.example.digital_payment.notification.application.port.in.GreetNotificationUserUseCase;
import com.example.digital_payment.notification.application.port.out.SaveNotificationPort;
import com.example.digital_payment.notification.domain.model.entities.Notifications;
import com.example.digital_payment.notification.domain.model.valueobjects.NotificationCreateData;

public class GreetNotificationService implements GreetNotificationUserUseCase {
    private final SaveNotificationPort saveNotificationPort;

    public GreetNotificationService(SaveNotificationPort saveNotificationPort) {
        this.saveNotificationPort = saveNotificationPort;
    }

    @Override
    public void handle(CreateNotificationCommand command) {
        NotificationCreateData createData = new NotificationCreateData(command.userId(),
            command.title(), command.message(), command.type());
        saveNotificationPort.save(Notifications.create(createData));
    }

}
