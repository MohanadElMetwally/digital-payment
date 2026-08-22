package com.example.digital_payment.audit.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.digital_payment.audit.api.dto.AuditLogsResponse;
import com.example.digital_payment.audit.api.facade.AuditLogFacade;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {
    private final AuditLogFacade auditLogFacade;

    public AuditLogController(AuditLogFacade auditLogFacade) {
        this.auditLogFacade = auditLogFacade;
    }

    @PreAuthorize("hasAnyAuthority('SUPERUSER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<AuditLogsResponse> readAll() {
        return ResponseEntity.status(HttpStatus.OK).body(auditLogFacade.readAll());
    }
}
