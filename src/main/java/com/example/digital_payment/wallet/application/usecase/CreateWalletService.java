package com.example.digital_payment.wallet.application.usecase;

import com.example.digital_payment.shared.events.UserRegisteredEvent;
import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletCreationData;

public class CreateWalletService implements CreateWalletUseCase {

    private final SaveWalletPort saveWalletPort;

    public CreateWalletService(SaveWalletPort saveWalletPort) {
        this.saveWalletPort = saveWalletPort;
    }

    public void handle(UserRegisteredEvent event) {
        Wallets wallet = Wallets
            .createForUser(new WalletCreationData(event.userId(), event.currency()));
        saveWalletPort.save(wallet);
    }
}