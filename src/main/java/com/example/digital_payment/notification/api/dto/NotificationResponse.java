package com.example.digital_payment.notification.api.dto;

import java.util.UUID;

public record NotificationResponse(UUID id, UUID userId, String title, String message, String type,
        Boolean isRead, String readAt, String createdAt) {

}
