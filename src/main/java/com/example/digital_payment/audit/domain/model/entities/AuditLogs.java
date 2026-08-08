package com.example.digital_payment.audit.domain.model.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;
import com.example.digital_payment.audit.domain.model.snapshots.AuditLogsSnapshot;
import com.example.digital_payment.audit.domain.model.valueobjects.AuditLogCreateData;

public class AuditLogs {
    private UUID id;
    private UUID userId;
    private AuditAction action;
    private EntityType entityType;
    private UUID entityId;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdAt;

    public AuditLogs() {
    }

    public static AuditLogs record(AuditLogCreateData createdData) {
        AuditLogs log = new AuditLogs();
        log.id = UUID.randomUUID();
        log.userId = createdData.userId();
        log.action = createdData.action();
        log.entityType = createdData.entityType();
        log.entityId = createdData.entityId();
        log.oldValue = createdData.oldValue();
        log.newValue = createdData.newValue();
        log.createdAt = LocalDateTime.now();
        return log;
    }

    public static AuditLogs reconstitute(AuditLogsSnapshot snapshot) {
        AuditLogs log = new AuditLogs();
        log.id = snapshot.id();
        log.userId = snapshot.userId();
        log.action = snapshot.action();
        log.entityType = snapshot.entityType();
        log.entityId = snapshot.entityId();
        log.oldValue = snapshot.oldValue();
        log.newValue = snapshot.newValue();
        log.createdAt = snapshot.createdAt();
        return log;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public AuditAction getAction() {
        return action;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
