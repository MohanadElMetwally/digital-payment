package com.example.digital_payment.wallet.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletStatus;

public record WalletResponse(UUID id, UUID userId, WalletStatus status, BigDecimal balance,
    String currency, String createdAt) {

}
