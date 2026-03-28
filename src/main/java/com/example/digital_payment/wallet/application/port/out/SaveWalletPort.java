package com.example.digital_payment.wallet.application.port.out;

import com.example.digital_payment.wallet.domain.model.Wallets;

public interface SaveWalletPort {
    void save(Wallets event);
}
