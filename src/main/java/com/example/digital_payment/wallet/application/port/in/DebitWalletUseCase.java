package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.DebitWalletCommand;

public interface DebitWalletUseCase {
    void debit(DebitWalletCommand command);
}
