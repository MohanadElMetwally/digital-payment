package com.example.digital_payment.wallet.application.mapper;

import com.example.digital_payment.wallet.application.dto.WalletResult;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;

public class WalletMapper {
    public WalletResult toResult(Wallets wallet) {
        return new WalletResult(wallet.getId(), wallet.getUserId(), wallet.getBalance(),
            wallet.getCurrency(), wallet.getCreatedAt());
    }
}
