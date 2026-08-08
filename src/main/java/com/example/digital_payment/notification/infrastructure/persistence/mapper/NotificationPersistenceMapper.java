package com.example.digital_payment.notification.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.notification.domain.model.entities.Notifications;
import com.example.digital_payment.notification.domain.model.snapshots.NotificationSnapshot;
import com.example.digital_payment.notification.infrastructure.persistence.entity.NotificationEntity;

@Component
public class NotificationPersistenceMapper {
    public NotificationEntity toEntity(Notifications notification) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(notification.getId());
        entity.setUserId(notification.getUserId());
        entity.setTitle(notification.getTitle());
        entity.setMessage(notification.getMessage());
        entity.setType(notification.getType());
        entity.setIsRead(notification.getIsRead());
        entity.setReadAt(notification.getReadAt());
        entity.setCreatedAt(notification.getCreatedAt());
        return entity;
    }

    public Notifications toDomain(NotificationEntity entity) {
        NotificationSnapshot snapshot = new NotificationSnapshot(entity.getId(), entity.getUserId(),
            entity.getTitle(), entity.getMessage(), entity.getType(), entity.getIsRead(),
            entity.getReadAt(), entity.getCreatedAt());
        return Notifications.reconstitute(snapshot);
    }

    public NotificationEntity UpdateEntity(NotificationEntity entity, Notifications notification) {
        entity.setIsRead(notification.getIsRead());
        entity.setReadAt(notification.getReadAt());
        return entity;
    }
}
