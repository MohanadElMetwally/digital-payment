package com.example.digital_payment.wallet.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;

public record WalletTransactionCreationData(UUID walletId, UUID transactionId,
    WalletTransactionType type, BigDecimal amount, BigDecimal balanceAfter) {
}