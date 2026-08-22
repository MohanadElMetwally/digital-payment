package com.example.digital_payment.wallet.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.exceptions.InvalidWalletDataException;
import com.example.digital_payment.wallet.domain.model.snapshots.WalletSnapshot;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletCreationData;

public class Wallets {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;

    public static Wallets createForUser(WalletCreationData creationData) {
        if (creationData.userId() == null)
            throw new InvalidWalletDataException("UserId cannot be null");
        if (creationData.currency() == null || creationData.currency().isBlank())
            throw new InvalidWalletDataException("Currency cannot be blank");

        Wallets wallet = new Wallets();
        wallet.id = UUID.randomUUID();
        wallet.userId = creationData.userId();
        wallet.balance = BigDecimal.ZERO;
        wallet.currency = creationData.currency().toUpperCase().trim();
        wallet.createdAt = LocalDateTime.now();
        return wallet;
    }

    public static Wallets reconstitute(WalletSnapshot snapshot) {
        Wallets wallet = new Wallets();
        wallet.id = snapshot.id();
        wallet.userId = snapshot.userId();
        wallet.balance = snapshot.balance();
        wallet.currency = snapshot.currency();
        wallet.createdAt = snapshot.createdAt();
        return wallet;
    }

    public void debit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Debit amount must be greater than zero");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Amount is higher than wallet available balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Credit amount must be greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    public boolean belongsTo(UUID userId) {
        return this.getUserId().equals(userId);
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
