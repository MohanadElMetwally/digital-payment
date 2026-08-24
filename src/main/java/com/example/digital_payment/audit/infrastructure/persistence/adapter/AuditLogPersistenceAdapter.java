package com.example.digital_payment.audit.infrastructure.persistence.adapter;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.audit.application.port.out.LoadAuditLogsPort;
import com.example.digital_payment.audit.application.port.out.SaveAuditLogPort;
import com.example.digital_payment.audit.domain.model.entities.AuditLogs;
import com.example.digital_payment.audit.infrastructure.persistence.entity.AuditLogEntity;
import com.example.digital_payment.audit.infrastructure.persistence.mapper.AuditLogPersistenceMapper;
import com.example.digital_payment.audit.infrastructure.persistence.repository.AuditLogJpaRepository;

@Component
public class AuditLogPersistenceAdapter implements SaveAuditLogPort, LoadAuditLogsPort {
    private final AuditLogJpaRepository auditLogJpaRepository;
    private final AuditLogPersistenceMapper auditLogPersistenceMapper;

    public AuditLogPersistenceAdapter(AuditLogJpaRepository auditLogJpaRepository,
            AuditLogPersistenceMapper auditLogPersistenceMapper) {
        this.auditLogJpaRepository = auditLogJpaRepository;
        this.auditLogPersistenceMapper = auditLogPersistenceMapper;
    }

    @Override
    @Transactional
    public void save(AuditLogs auditLog) {
        AuditLogEntity entity = auditLogPersistenceMapper.toEntity(auditLog);
        auditLogJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogs> findAll() {
        return auditLogJpaRepository.findAll().stream().map(auditLogPersistenceMapper::toDomain)
                .toList();
    }
}
