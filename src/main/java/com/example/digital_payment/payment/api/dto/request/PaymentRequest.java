package com.example.digital_payment.payment.api.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(@NotNull UUID billId, @NotNull UUID creditCardId, boolean useWallet,
    UUID walletId) {
    @AssertTrue(message = "walletId is required when useWallet is true")
    public boolean isWalletSelectionConsistent() {
        return !useWallet || walletId != null;
    }
}