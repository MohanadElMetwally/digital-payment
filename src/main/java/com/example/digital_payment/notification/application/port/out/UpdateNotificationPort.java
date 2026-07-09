package com.example.digital_payment.notification.application.port.out;

import java.util.UUID;

import com.example.digital_payment.notification.domain.model.entities.Notifications;

public interface UpdateNotificationPort {
    void UpdateNotification(UUID id, Notifications notification);
}
