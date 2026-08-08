package com.example.digital_payment.wallet.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.application.dto.WalletResult;
import com.example.digital_payment.wallet.application.mapper.WalletMapper;
import com.example.digital_payment.wallet.application.port.in.GetWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.domain.exceptions.WalletNotFoundException;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;

@Component
public class GetWalletService implements GetWalletUseCase {
    private final LoadWalletPort loadWalletPort;
    private final WalletMapper walletMapper;

    public GetWalletService(LoadWalletPort loadWalletPort, WalletMapper walletMapper) {
        this.loadWalletPort = loadWalletPort;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletResult getByUserId(UUID userId) {
        Wallets wallet = loadWalletPort.getByUserId(userId)
            .orElseThrow(() -> new WalletNotFoundException(userId));
        return walletMapper.toResult(wallet);
    }
}
