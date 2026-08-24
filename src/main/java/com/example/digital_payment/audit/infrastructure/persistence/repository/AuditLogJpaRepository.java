package com.example.digital_payment.audit.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.digital_payment.audit.infrastructure.persistence.entity.AuditLogEntity;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, UUID> {

}
