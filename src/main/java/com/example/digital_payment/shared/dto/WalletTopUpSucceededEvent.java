package com.example.digital_payment.shared.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletTopUpSucceededEvent(UUID transactionId, UUID walletId, BigDecimal amount) {

}
