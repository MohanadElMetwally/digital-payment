package com.example.digital_payment.audit.application.dto;

import java.util.UUID;

import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;

public record CreateAuditLogCommand(UUID userId, AuditAction action, EntityType entityType,
    UUID entityId, String oldValue, String newValue) {

}
