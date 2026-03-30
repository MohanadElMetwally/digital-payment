package com.example.digital_payment.wallet.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletStatus;

public record WalletResult(UUID id, UUID userId, WalletStatus status, BigDecimal balance,
    String currency, LocalDateTime createdAt) {

}
