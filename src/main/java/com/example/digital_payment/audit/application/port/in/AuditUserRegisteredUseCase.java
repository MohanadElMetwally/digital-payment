package com.example.digital_payment.audit.application.port.in;

import com.example.digital_payment.audit.application.dto.CreateAuditLogCommand;

public interface AuditUserRegisteredUseCase {
    void handle(CreateAuditLogCommand command);
}
