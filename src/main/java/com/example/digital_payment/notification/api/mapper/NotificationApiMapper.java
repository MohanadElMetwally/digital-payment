package com.example.digital_payment.notification.api.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.notification.api.dto.NotificationResponse;
import com.example.digital_payment.notification.api.dto.NotificationsResponse;
import com.example.digital_payment.notification.application.dto.NotificationResult;
import com.example.digital_payment.notification.application.dto.NotificationsResult;

@Component
public class NotificationApiMapper {
    public NotificationResponse toResponse(NotificationResult result) {
        return new NotificationResponse(result.id(), result.userId(), result.title(),
                result.message(), result.type().toString(), result.isRead(),
                result.readAt() != null ? result.readAt().toString() : null,
                result.createdAt().toString());
    }

    public NotificationsResponse toResponseList(NotificationsResult result) {
        return new NotificationsResponse(
                result.notifications().stream().map(this::toResponse).toList());
    }
}
