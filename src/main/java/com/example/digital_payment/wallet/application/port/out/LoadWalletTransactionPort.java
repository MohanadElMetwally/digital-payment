package com.example.digital_payment.wallet.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;

public interface LoadWalletTransactionPort {
    Optional<WalletTransactions> findById(UUID id);
}
