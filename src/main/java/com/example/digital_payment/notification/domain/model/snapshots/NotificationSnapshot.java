package com.example.digital_payment.notification.domain.model.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.notification.domain.enums.NotificationType;

public record NotificationSnapshot(UUID id, UUID userId, String title, String message,
        NotificationType type, Boolean isRead, LocalDateTime readAt, LocalDateTime createdAt) {

}
