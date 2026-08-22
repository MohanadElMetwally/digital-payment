package com.example.digital_payment.payment.api.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WalletTopUpRequest(@NotNull UUID walletId, @NotNull UUID creditCardId,
        @NotNull @Positive BigDecimal amount) {

}
