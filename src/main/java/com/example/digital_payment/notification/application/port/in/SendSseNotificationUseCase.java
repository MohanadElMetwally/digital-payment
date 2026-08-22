package com.example.digital_payment.notification.application.port.in;

import java.util.UUID;
import com.example.digital_payment.shared.dto.NotificationMessage;

public interface SendSseNotificationUseCase {
    void handle(UUID userId, NotificationMessage<?> notificationMessage);
}
