package com.example.digital_payment.audit.application.usecase;

import java.util.List;

import com.example.digital_payment.audit.application.dto.AuditLogsResult;
import com.example.digital_payment.audit.application.mapper.AuditLogMapper;
import com.example.digital_payment.audit.application.port.in.LoadAuditLogsUseCase;
import com.example.digital_payment.audit.application.port.out.LoadAuditLogsPort;
import com.example.digital_payment.audit.domain.model.entities.AuditLogs;

public class LoadAuditLogsService implements LoadAuditLogsUseCase {
    private final LoadAuditLogsPort loadAuditLogsPort;
    private final AuditLogMapper auditLogMapper;

    public LoadAuditLogsService(LoadAuditLogsPort loadAuditLogsPort,
        AuditLogMapper auditLogMapper) {
        this.loadAuditLogsPort = loadAuditLogsPort;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    public AuditLogsResult readAll() {
        List<AuditLogs> logs = loadAuditLogsPort.findAll();
        return auditLogMapper.toResultList(logs);
    }
}
