package com.example.digital_payment.notification.application.mapper;

import java.util.List;
import com.example.digital_payment.notification.application.dto.NotificationResult;
import com.example.digital_payment.notification.application.dto.NotificationsResult;
import com.example.digital_payment.notification.domain.model.entities.Notifications;

public class NotificationMapper {
    public NotificationResult toResult(Notifications notification) {
        return new NotificationResult(notification.getId(), notification.getUserId(),
                notification.getTitle(), notification.getMessage(), notification.getType(),
                notification.getIsRead(), notification.getReadAt(), notification.getCreatedAt());
    }

    public NotificationsResult toResultList(List<Notifications> notifications) {
        return new NotificationsResult(notifications.stream().map(this::toResult).toList());
    }
}
