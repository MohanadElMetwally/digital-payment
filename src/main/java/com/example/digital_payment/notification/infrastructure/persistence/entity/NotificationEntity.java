package com.example.digital_payment.notification.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.notification.domain.enums.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    private UUID id;
    private UUID userId;
    private String title;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
}
