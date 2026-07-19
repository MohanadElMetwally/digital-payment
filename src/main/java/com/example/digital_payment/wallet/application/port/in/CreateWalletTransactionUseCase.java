package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.CreateWalletTransactionCommand;

public interface CreateWalletTransactionUseCase {
    void create(CreateWalletTransactionCommand command);
}
