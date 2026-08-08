package com.example.digital_payment.wallet.application.port.in;

import java.util.UUID;

import com.example.digital_payment.wallet.application.dto.WalletResult;

public interface GetWalletUseCase {
    WalletResult getByUserId(UUID userId);
}
