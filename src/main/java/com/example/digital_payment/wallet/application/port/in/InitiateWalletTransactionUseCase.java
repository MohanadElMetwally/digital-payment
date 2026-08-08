package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.InitiateWalletTransactionCommand;

public interface InitiateWalletTransactionUseCase {
    void initiate(InitiateWalletTransactionCommand command);
}
