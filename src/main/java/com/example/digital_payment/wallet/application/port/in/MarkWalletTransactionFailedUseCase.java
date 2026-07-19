package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.wallet.application.dto.MarkWalletTransactionFailedCommand;

public interface MarkWalletTransactionFailedUseCase {
    void mark(MarkWalletTransactionFailedCommand command);
}
