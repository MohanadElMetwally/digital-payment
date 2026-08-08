package com.example.digital_payment.audit.api.dto;

import java.util.UUID;

public record AuditLogResponse(UUID id, UUID userId, String action, String entityType,
    UUID entityId, String oldValue, String newValue, String createdAt) {
}
