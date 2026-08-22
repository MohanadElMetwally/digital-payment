package com.example.digital_payment.billing.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.digital_payment.billing.infrastructure.persistence.entity.BillerEntity;

public interface BillerJpaRepository extends JpaRepository<BillerEntity, UUID> {

}
