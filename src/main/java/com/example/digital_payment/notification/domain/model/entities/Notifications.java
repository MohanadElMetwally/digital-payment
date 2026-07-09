package com.example.digital_payment.notification.domain.model.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.notification.domain.enums.NotificationType;
import com.example.digital_payment.notification.domain.model.snapshots.NotificationSnapshot;
import com.example.digital_payment.notification.domain.model.valueobjects.NotificationCreateData;

public class Notifications {
    private UUID id;
    private UUID userId;
    private String title;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    public Notifications() {

    }

    public static Notifications create(NotificationCreateData createData) {
        Notifications notification = new Notifications();
        notification.id = UUID.randomUUID();
        notification.userId = createData.userId();
        notification.title = createData.title();
        notification.message = createData.message();
        notification.type = createData.type();
        notification.isRead = false;
        notification.readAt = null;
        notification.createdAt = LocalDateTime.now();
        return notification;
    }

    public static Notifications reconstitute(NotificationSnapshot snapshot) {
        Notifications notification = new Notifications();
        notification.id = snapshot.id();
        notification.userId = snapshot.userId();
        notification.title = snapshot.title();
        notification.message = snapshot.message();
        notification.type = snapshot.type();
        notification.isRead = snapshot.isRead();
        notification.readAt = snapshot.readAt();
        notification.createdAt = snapshot.createdAt();
        return notification;
    }

    public void markRead() {
        if (!this.isRead) {
            this.isRead = true;
            this.readAt = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
