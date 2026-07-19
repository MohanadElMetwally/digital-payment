package com.example.digital_payment.wallet.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletResult(UUID id, UUID userId, BigDecimal balance, String currency,
    LocalDateTime createdAt) {

}
