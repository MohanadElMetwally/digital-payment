package com.example.digital_payment.wallet.application.usecase;

import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.wallet.application.dto.MarkWalletTransactionFailedCommand;
import com.example.digital_payment.wallet.application.port.in.MarkWalletTransactionFailedUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionByTransactionId;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletTransactionPort;
import com.example.digital_payment.wallet.domain.exceptions.WalletTransactionNotFoundException;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;

public class MarkWalletTransactionFailedService implements MarkWalletTransactionFailedUseCase {
    private final TransactionPort transactionPort;
    private final LoadWalletTransactionByTransactionId loadWalletTransactionPort;
    private final UpdateWalletTransactionPort updateWalletTransactionPort;

    public MarkWalletTransactionFailedService(TransactionPort transactionPort,
        LoadWalletTransactionByTransactionId loadWalletTransactionPort,
        UpdateWalletTransactionPort updateWalletTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadWalletTransactionPort = loadWalletTransactionPort;
        this.updateWalletTransactionPort = updateWalletTransactionPort;
    }

    @Override
    public void mark(MarkWalletTransactionFailedCommand command) {
        transactionPort.executeVoid(() -> {
            WalletTransactions wtx = loadWalletTransactionPort
                .findByTransactionId(command.transactionId())
                .orElseThrow(() -> new WalletTransactionNotFoundException());
            wtx.markFailed();
            updateWalletTransactionPort.update(wtx);
        });
    }

}
