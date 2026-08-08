package com.example.digital_payment.wallet.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;

public record InitiateWalletTransactionCommand(UUID walletId, UUID transactionId,
    WalletTransactionType type, BigDecimal amount) {

}
