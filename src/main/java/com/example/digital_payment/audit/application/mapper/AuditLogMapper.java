package com.example.digital_payment.audit.application.mapper;

import java.util.List;

import com.example.digital_payment.audit.application.dto.AuditLogResult;
import com.example.digital_payment.audit.application.dto.AuditLogsResult;
import com.example.digital_payment.audit.domain.model.entities.AuditLogs;

public class AuditLogMapper {
    public AuditLogResult toResult(AuditLogs log) {
        return new AuditLogResult(log.getId(), log.getUserId(), log.getAction(),
            log.getEntityType(), log.getEntityId(), log.getOldValue(), log.getNewValue(),
            log.getCreatedAt());
    }

    public AuditLogsResult toResultList(List<AuditLogs> logs) {
        return new AuditLogsResult(logs.stream().map(this::toResult).toList());
    }
}
