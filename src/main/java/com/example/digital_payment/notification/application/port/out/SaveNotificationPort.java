package com.example.digital_payment.notification.application.port.out;

import com.example.digital_payment.notification.domain.model.entities.Notifications;

public interface SaveNotificationPort {
    void save(Notifications notification);
}
