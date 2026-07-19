package com.example.digital_payment.wallet.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "wallets")
public class WalletEntity {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;
}