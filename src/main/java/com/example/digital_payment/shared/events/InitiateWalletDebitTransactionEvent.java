package com.example.digital_payment.shared.events;

import java.math.BigDecimal;
import java.util.UUID;

public record InitiateWalletDebitTransactionEvent(UUID walletId, UUID transactionId,
    BigDecimal amount) {

}
