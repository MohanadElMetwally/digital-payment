package com.example.digital_payment.audit.domain.model.valueobjects;

import java.util.UUID;
import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;

public record AuditLogCreateData(UUID userId, AuditAction action, EntityType entityType,
        UUID entityId, String oldValue, String newValue) {
}
