package com.example.digital_payment.wallet.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(UUID id, UUID userId, BigDecimal balance, String currency,
        String createdAt) {

}
