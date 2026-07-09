package com.example.digital_payment.notification.domain.model.valueobjects;

import java.util.UUID;

import com.example.digital_payment.notification.domain.enums.NotificationType;

public record NotificationCreateData(UUID userId, String title, String message,
    NotificationType type) {

}
