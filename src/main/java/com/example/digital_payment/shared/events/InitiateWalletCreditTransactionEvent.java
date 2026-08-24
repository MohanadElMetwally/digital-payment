package com.example.digital_payment.shared.events;

import java.math.BigDecimal;
import java.util.UUID;

public record InitiateWalletCreditTransactionEvent(UUID walletId, UUID transactionId,
        BigDecimal amount) {

}
