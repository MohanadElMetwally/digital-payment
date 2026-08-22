package com.example.digital_payment.audit.application.usecase;

import com.example.digital_payment.audit.application.dto.CreateAuditLogCommand;
import com.example.digital_payment.audit.application.port.in.AuditUserRegisteredUseCase;
import com.example.digital_payment.audit.application.port.out.SaveAuditLogPort;
import com.example.digital_payment.audit.domain.model.entities.AuditLogs;
import com.example.digital_payment.audit.domain.model.valueobjects.AuditLogCreateData;

public class AuditUserRegisteredService implements AuditUserRegisteredUseCase {
    private final SaveAuditLogPort saveAuditLogPort;

    public AuditUserRegisteredService(SaveAuditLogPort saveAuditLogPort) {
        this.saveAuditLogPort = saveAuditLogPort;
    }

    @Override
    public void handle(CreateAuditLogCommand command) {
        AuditLogs log = AuditLogs.record(new AuditLogCreateData(command.userId(), command.action(),
                command.entityType(), command.userId(), command.oldValue(), command.newValue()));
        saveAuditLogPort.save(log);
    }

}
