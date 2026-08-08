package com.example.digital_payment.audit.application.port.out;

import java.util.List;

import com.example.digital_payment.audit.domain.model.entities.AuditLogs;

public interface LoadAuditLogsPort {
    List<AuditLogs> findAll();
}
