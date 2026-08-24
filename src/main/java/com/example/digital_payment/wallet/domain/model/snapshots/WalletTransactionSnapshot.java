package com.example.digital_payment.wallet.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionStatus;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;

public record WalletTransactionSnapshot(UUID id, UUID walletId, UUID transactionId,
        WalletTransactionType type, WalletTransactionStatus status, BigDecimal balanceAfter,
        BigDecimal amount, LocalDateTime createdAt, LocalDateTime completedAt) {

}
