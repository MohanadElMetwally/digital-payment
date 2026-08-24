package com.example.digital_payment.wallet.application.usecase;

import com.example.digital_payment.wallet.application.dto.CreateWalletCommand;
import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletCreationData;

public class CreateWalletService implements CreateWalletUseCase {

    private final SaveWalletPort saveWalletPort;

    public CreateWalletService(SaveWalletPort saveWalletPort) {
        this.saveWalletPort = saveWalletPort;
    }

    @Override
    public void handle(CreateWalletCommand command) {
        Wallets wallet =
                Wallets.createForUser(new WalletCreationData(command.userId(), command.currency()));
        saveWalletPort.save(wallet);
    }
}
