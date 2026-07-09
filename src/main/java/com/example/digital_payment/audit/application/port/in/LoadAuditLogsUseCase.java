package com.example.digital_payment.audit.application.port.in;

import com.example.digital_payment.audit.application.dto.AuditLogsResult;

public interface LoadAuditLogsUseCase {
    AuditLogsResult readAll();
}
