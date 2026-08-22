package com.example.digital_payment.wallet.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionStatus;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;
import com.example.digital_payment.wallet.domain.model.snapshots.WalletTransactionSnapshot;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletTransactionCreationData;

public class WalletTransactions {
    private UUID id;
    private UUID walletId;
    private UUID transactionId;
    private WalletTransactionType type;
    private WalletTransactionStatus status;
    private BigDecimal balanceAfter;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public WalletTransactions() {

    }

    public static WalletTransactions create(WalletTransactionCreationData creationData) {
        WalletTransactions wtx = new WalletTransactions();
        wtx.id = UUID.randomUUID();
        wtx.walletId = creationData.walletId();
        wtx.transactionId = creationData.transactionId();
        wtx.type = creationData.type();
        wtx.status = WalletTransactionStatus.PENDING;
        wtx.amount = creationData.amount();
        wtx.balanceAfter = creationData.balanceAfter();
        wtx.createdAt = LocalDateTime.now();
        wtx.completedAt = null;
        return wtx;
    }

    public static WalletTransactions reconstitute(WalletTransactionSnapshot snapshot) {
        WalletTransactions wtx = new WalletTransactions();
        wtx.id = snapshot.id();
        wtx.walletId = snapshot.walletId();
        wtx.transactionId = snapshot.transactionId();
        wtx.type = snapshot.type();
        wtx.status = snapshot.status();
        wtx.balanceAfter = snapshot.balanceAfter();
        wtx.amount = snapshot.amount();
        wtx.createdAt = snapshot.createdAt();
        wtx.completedAt = snapshot.completedAt();
        return wtx;
    }

    public void markFailed() {
        if (this.status != WalletTransactionStatus.PENDING) {
            return;
        }

        this.status = WalletTransactionStatus.FAILED;
        this.completedAt = LocalDateTime.now();
    }

    public void markSucceeded() {
        if (this.status != WalletTransactionStatus.PENDING) {
            return;
        }

        this.status = WalletTransactionStatus.SUCCESS;
        this.completedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public WalletTransactionType getType() {
        return type;
    }

    public WalletTransactionStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

}
