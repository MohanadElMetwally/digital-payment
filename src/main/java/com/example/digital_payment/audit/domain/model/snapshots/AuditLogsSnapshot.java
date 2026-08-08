package com.example.digital_payment.audit.domain.model.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;

public record AuditLogsSnapshot(UUID id, UUID userId, AuditAction action, EntityType entityType,
    UUID entityId, String oldValue, String newValue, LocalDateTime createdAt) {
}
