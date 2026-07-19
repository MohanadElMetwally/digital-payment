package com.example.digital_payment.shared.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreditWalletEvent(UUID userId, UUID walletId, UUID transactionId, BigDecimal amount) {

}
