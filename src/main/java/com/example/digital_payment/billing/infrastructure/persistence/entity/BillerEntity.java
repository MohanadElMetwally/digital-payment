package com.example.digital_payment.billing.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillCategory;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "billers")
public class BillerEntity {
    @Id
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private BillCategory category;
    @Enumerated(EnumType.STRING)
    private ServiceProvider serviceProvider;
    private LocalDateTime createdAt;
}
