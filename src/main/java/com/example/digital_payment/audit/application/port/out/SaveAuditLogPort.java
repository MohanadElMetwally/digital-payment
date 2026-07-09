package com.example.digital_payment.audit.application.port.out;

import com.example.digital_payment.audit.domain.model.entities.AuditLogs;

public interface SaveAuditLogPort {
    void save(AuditLogs auditLog);
}
