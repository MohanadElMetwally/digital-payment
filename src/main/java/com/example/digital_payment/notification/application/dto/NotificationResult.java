package com.example.digital_payment.notification.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.notification.domain.enums.NotificationType;

public record NotificationResult(UUID id, UUID userId, String title, String message,
    NotificationType type, Boolean isRead, LocalDateTime readAt, LocalDateTime createdAt) {

}
