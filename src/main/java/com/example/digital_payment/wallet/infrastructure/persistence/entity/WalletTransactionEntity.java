package com.example.digital_payment.wallet.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionStatus;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "wallet_transactions")
public class WalletTransactionEntity {
    @Id
    private UUID id;
    private UUID walletId;
    private UUID transactionId;
    @Enumerated(EnumType.STRING)
    private WalletTransactionType type;
    @Enumerated(EnumType.STRING)
    private WalletTransactionStatus status;
    private BigDecimal balanceAfter;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
