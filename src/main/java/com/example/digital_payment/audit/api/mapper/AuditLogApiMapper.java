package com.example.digital_payment.audit.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.audit.api.dto.AuditLogResponse;
import com.example.digital_payment.audit.api.dto.AuditLogsResponse;
import com.example.digital_payment.audit.application.dto.AuditLogResult;
import com.example.digital_payment.audit.application.dto.AuditLogsResult;

@Component
public class AuditLogApiMapper {
    public AuditLogResponse toResponse(AuditLogResult result) {
        return new AuditLogResponse(result.id(), result.userId(), result.action().toString(),
            result.entityType() != null ? result.entityType().name() : null, result.entityId(),
            result.oldValue(), result.newValue(), result.createdAt().toString());
    }

    public AuditLogsResponse toResponseList(AuditLogsResult result) {
        return new AuditLogsResponse(result.auditLogs().stream().map(this::toResponse).toList());
    }
}
