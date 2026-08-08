package com.example.digital_payment.notification.application.port.out;

import java.util.UUID;

import com.example.digital_payment.shared.dto.NotificationMessage;

public interface NotificationPublisherPort {
    void publish(UUID userId, NotificationMessage<?> message);
}
