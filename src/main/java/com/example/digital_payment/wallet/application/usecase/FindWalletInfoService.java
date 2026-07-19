package com.example.digital_payment.wallet.application.usecase;

import java.util.UUID;

import com.example.digital_payment.shared.application.port.in.FindWalletInfoUseCase;
import com.example.digital_payment.shared.dto.WalletInfo;
import com.example.digital_payment.shared.exception.ForbiddenException;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;

public class FindWalletInfoService implements FindWalletInfoUseCase {
    private final LoadWalletPort loadWalletPort;

    public FindWalletInfoService(LoadWalletPort loadWalletPort) {
        this.loadWalletPort = loadWalletPort;
    }

    @Override
    public WalletInfo fetchWalletInfo(UUID walletId, UUID userId) {
        Wallets wallet = loadWalletPort.getById(walletId)
            .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        if (!wallet.belongsTo(userId)) {
            throw new ForbiddenException("Access to wallet denied");
        }
        return new WalletInfo(wallet.getId(), wallet.getCurrency(), wallet.getBalance());
    }

}
