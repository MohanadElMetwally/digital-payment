package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.CreditWalletCommand;

public interface CreditWalletUseCase {
    void credit(CreditWalletCommand command);
}
