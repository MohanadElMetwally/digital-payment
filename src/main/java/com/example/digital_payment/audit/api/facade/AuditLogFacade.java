package com.example.digital_payment.audit.api.facade;

import org.springframework.stereotype.Component;
import com.example.digital_payment.audit.api.dto.AuditLogsResponse;
import com.example.digital_payment.audit.api.mapper.AuditLogApiMapper;
import com.example.digital_payment.audit.application.dto.AuditLogsResult;
import com.example.digital_payment.audit.application.port.in.LoadAuditLogsUseCase;

@Component
public class AuditLogFacade {
    private final LoadAuditLogsUseCase loadAuditLogsUseCase;
    private final AuditLogApiMapper auditLogApiMapper;

    public AuditLogFacade(LoadAuditLogsUseCase loadAuditLogsUseCase,
            AuditLogApiMapper auditLogApiMapper) {
        this.loadAuditLogsUseCase = loadAuditLogsUseCase;
        this.auditLogApiMapper = auditLogApiMapper;
    }

    public AuditLogsResponse readAll() {
        AuditLogsResult result = loadAuditLogsUseCase.readAll();
        return auditLogApiMapper.toResponseList(result);
    }
}
