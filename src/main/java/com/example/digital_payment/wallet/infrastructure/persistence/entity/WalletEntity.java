package com.example.digital_payment.wallet.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private WalletStatus status;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;
}