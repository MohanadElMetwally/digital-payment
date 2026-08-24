package com.example.digital_payment.notification.application.usecase;

import java.util.UUID;
import com.example.digital_payment.notification.application.port.in.SendSseNotificationUseCase;
import com.example.digital_payment.notification.application.port.out.NotificationPublisherPort;
import com.example.digital_payment.shared.dto.NotificationMessage;

public class SendSseNotificationService implements SendSseNotificationUseCase {
    private final NotificationPublisherPort notificationPublisherPort;

    public SendSseNotificationService(NotificationPublisherPort notificationPublisherPort) {
        this.notificationPublisherPort = notificationPublisherPort;
    }

    @Override
    public void handle(UUID userId, NotificationMessage<?> notificationMessage) {
        notificationPublisherPort.publish(userId, notificationMessage);
    }

}
