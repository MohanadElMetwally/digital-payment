package com.example.digital_payment.audit.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {
    @Id
    private UUID id;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private AuditAction action;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    private UUID entityId;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdAt;
}
