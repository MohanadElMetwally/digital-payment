package com.example.digital_payment.wallet.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DebitWalletCommand(UUID userId, UUID walletId, UUID transactionId,
    BigDecimal amount) {

}
