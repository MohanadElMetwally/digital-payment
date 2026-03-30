package com.example.digital_payment.wallet.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.enums.WalletStatus;
import com.example.digital_payment.wallet.domain.exceptions.InvalidWalletDataException;
import com.example.digital_payment.wallet.domain.model.snapshots.WalletSnapshot;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletCreationData;

public class Wallets {
    private UUID id;
    private UUID userId;
    private WalletStatus status;
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
        wallet.status = WalletStatus.ACTIVE;
        wallet.balance = BigDecimal.ZERO;
        wallet.currency = creationData.currency().toUpperCase().trim();
        wallet.createdAt = LocalDateTime.now();
        return wallet;
    }

    public static Wallets reconstitute(WalletSnapshot snapshot){
        Wallets wallet = new Wallets();
        wallet.id = snapshot.id();
        wallet.userId = snapshot.userId();
        wallet.status = snapshot.status();
        wallet.balance = snapshot.balance();
        wallet.currency = snapshot.currency();
        wallet.createdAt = snapshot.createdAt();
        return wallet;
    }

    public UUID getId() {
        return id;
    }

    public WalletStatus getStatus() {
        return status;
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
