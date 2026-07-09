package com.example.digital_payment.audit.api.dto;

import java.util.List;

public record AuditLogsResponse(List<AuditLogResponse> auditLogs) {
}
