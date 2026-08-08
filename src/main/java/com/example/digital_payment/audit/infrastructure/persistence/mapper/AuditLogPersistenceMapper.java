package com.example.digital_payment.audit.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.audit.domain.model.entities.AuditLogs;
import com.example.digital_payment.audit.domain.model.snapshots.AuditLogsSnapshot;
import com.example.digital_payment.audit.infrastructure.persistence.entity.AuditLogEntity;

@Component
public class AuditLogPersistenceMapper {
    public AuditLogEntity toEntity(AuditLogs log) {
        AuditLogEntity entity = new AuditLogEntity();
        entity.setId(log.getId());
        entity.setUserId(log.getUserId());
        entity.setAction(log.getAction());
        entity.setEntityType(log.getEntityType());
        entity.setEntityId(log.getEntityId());
        entity.setOldValue(log.getOldValue());
        entity.setNewValue(log.getNewValue());
        entity.setCreatedAt(log.getCreatedAt());
        return entity;
    }

    public AuditLogs toDomain(AuditLogEntity entity) {
        AuditLogsSnapshot snapshot = new AuditLogsSnapshot(entity.getId(), entity.getUserId(),
            entity.getAction(), entity.getEntityType(), entity.getEntityId(), entity.getOldValue(),
            entity.getNewValue(), entity.getCreatedAt());
        return AuditLogs.reconstitute(snapshot);
    }
}
