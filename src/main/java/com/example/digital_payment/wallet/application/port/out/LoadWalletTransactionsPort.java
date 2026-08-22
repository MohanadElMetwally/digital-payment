package com.example.digital_payment.wallet.application.port.out;

import java.util.List;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;

public interface LoadWalletTransactionsPort {
    List<WalletTransactions> loadWalletTransactions(UUID walletId);
}
