package com.example.digital_payment.notification.application.port.in;

import com.example.digital_payment.notification.application.dto.NotificationsResult;

public interface LoadNotificationsUseCase {
    NotificationsResult readAll();
}
