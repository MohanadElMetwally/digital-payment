package com.example.digital_payment.wallet.application.usecase;

import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.wallet.application.dto.DebitWalletCommand;
import com.example.digital_payment.wallet.application.port.in.DebitWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionByTransactionId;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletPort;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletTransactionPort;
import com.example.digital_payment.wallet.domain.exceptions.WalletAccessDenied;
import com.example.digital_payment.wallet.domain.exceptions.WalletNotFoundException;
import com.example.digital_payment.wallet.domain.exceptions.WalletTransactionNotFoundException;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;

public class DebitWalletService implements DebitWalletUseCase {
    private final TransactionPort transactionPort;
    private final LoadWalletPort loadWalletPort;
    private final UpdateWalletPort updateWalletPort;
    private final LoadWalletTransactionByTransactionId loadWalletTransactionPort;
    private final UpdateWalletTransactionPort updateWalletTransactionPort;

    public DebitWalletService(TransactionPort transactionPort, LoadWalletPort loadWalletPort,
        UpdateWalletPort updateWalletPort,
        LoadWalletTransactionByTransactionId loadWalletTransactionPort,
        UpdateWalletTransactionPort updateWalletTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadWalletPort = loadWalletPort;
        this.updateWalletPort = updateWalletPort;
        this.loadWalletTransactionPort = loadWalletTransactionPort;
        this.updateWalletTransactionPort = updateWalletTransactionPort;
    }

    @Override
    public void debit(DebitWalletCommand command) {
        transactionPort.executeVoid(() -> {
            Wallets wallet = loadWalletPort.getById(command.walletId())
                .orElseThrow(() -> new WalletNotFoundException(command.walletId()));
            if (!wallet.belongsTo(command.userId())) {
                throw new WalletAccessDenied();
            }
            WalletTransactions wtx = loadWalletTransactionPort
                .findByTransactionId(command.transactionId())
                .orElseThrow(() -> new WalletTransactionNotFoundException());
            wallet.debit(command.amount());
            wtx.markSucceeded();
            updateWalletPort.update(wallet);
            updateWalletTransactionPort.update(wtx);
        });
    }

}
