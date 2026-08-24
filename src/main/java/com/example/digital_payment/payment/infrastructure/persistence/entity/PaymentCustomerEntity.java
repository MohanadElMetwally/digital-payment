package com.example.digital_payment.payment.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_customers")
public class PaymentCustomerEntity {
    @Id
    private UUID userId;
    private String CustomerId;
    private LocalDateTime createdAt;
}
