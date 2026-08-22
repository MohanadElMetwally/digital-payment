package com.example.digital_payment.notification.application.port.out;

import java.util.List;
import com.example.digital_payment.notification.domain.model.entities.Notifications;

public interface LoadNotificationsPort {
    List<Notifications> findAll();
}
