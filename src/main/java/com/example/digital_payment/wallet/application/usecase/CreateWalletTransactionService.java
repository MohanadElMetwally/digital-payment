package com.example.digital_payment.wallet.application.usecase;

import java.math.BigDecimal;

import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.wallet.application.dto.CreateWalletTransactionCommand;
import com.example.digital_payment.wallet.application.port.in.CreateWalletTransactionUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletTransactionPort;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;
import com.example.digital_payment.wallet.domain.exceptions.WalletNotFoundException;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.domain.model.valueobjects.WalletTransactionCreationData;

public class CreateWalletTransactionService implements CreateWalletTransactionUseCase {
    private final TransactionPort transactionPort;
    private final LoadWalletPort loadWalletPort;
    private final SaveWalletTransactionPort saveWalletTransactionPort;

    public CreateWalletTransactionService(TransactionPort transactionPort,
        LoadWalletPort loadWalletPort, SaveWalletTransactionPort saveWalletTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadWalletPort = loadWalletPort;
        this.saveWalletTransactionPort = saveWalletTransactionPort;
    }

    @Override
    public void create(CreateWalletTransactionCommand command) {
        transactionPort.executeVoid(() -> {
            Wallets wallet = loadWalletPort.getById(command.walletId())
                .orElseThrow(() -> new WalletNotFoundException(command.walletId()));
            BigDecimal balanceAfter;
            if (command.type() == WalletTransactionType.CREDIT) {
                balanceAfter = wallet.getBalance().add(command.amount());
            } else {
                balanceAfter = wallet.getBalance().compareTo(command.amount()) < 0 ? BigDecimal.ZERO
                    : wallet.getBalance().subtract(command.amount());
            }
            WalletTransactionCreationData creationData = new WalletTransactionCreationData(
                command.walletId(), command.transactionId(), command.type(), command.amount(),
                balanceAfter);
            WalletTransactions wtx = WalletTransactions.create(creationData);
            saveWalletTransactionPort.save(wtx);
        });
    }

}
