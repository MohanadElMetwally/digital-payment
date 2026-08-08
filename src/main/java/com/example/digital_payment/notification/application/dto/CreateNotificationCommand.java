package com.example.digital_payment.notification.application.dto;

import java.util.UUID;

import com.example.digital_payment.notification.domain.enums.NotificationType;

public record CreateNotificationCommand(UUID userId, String title, String message,
    NotificationType type) {

}
