package com.example.digital_payment.wallet.application.port.out;

import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;

public interface SaveWalletTransactionPort {
    void save(WalletTransactions transaction);
}
