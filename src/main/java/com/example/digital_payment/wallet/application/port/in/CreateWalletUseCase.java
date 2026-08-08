package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.CreateWalletCommand;

public interface CreateWalletUseCase {
    void handle(CreateWalletCommand command);
}
