package com.example.digital_payment.wallet.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.exceptions.InvalidWalletDataException;

public class Wallets {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;

    public static Wallets createForUser(UUID userId, String currency) {
        if (userId == null)
            throw new InvalidWalletDataException("UserId cannot be null");
        if (currency == null || currency.isBlank())
            throw new InvalidWalletDataException("Currency cannot be blank");

        Wallets wallet = new Wallets();
        wallet.id = UUID.randomUUID();
        wallet.userId = userId;
        wallet.balance = BigDecimal.ZERO;
        wallet.currency = currency.toUpperCase().trim();
        wallet.createdAt = LocalDateTime.now();
        return wallet;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
