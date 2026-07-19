package com.example.digital_payment.wallet.application.port.out;

import com.example.digital_payment.wallet.domain.model.entities.Wallets;

public interface UpdateWalletPort {
    Wallets update(Wallets wallet);
}
