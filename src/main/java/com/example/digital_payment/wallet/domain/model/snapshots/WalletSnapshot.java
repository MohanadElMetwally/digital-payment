package com.example.digital_payment.wallet.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletSnapshot(UUID id, UUID userId, BigDecimal balance, String currency,
    LocalDateTime createdAt) {

}
