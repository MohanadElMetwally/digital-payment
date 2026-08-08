package com.example.digital_payment.shared.application.port.in;

import java.util.UUID;

import com.example.digital_payment.shared.dto.WalletInfo;

public interface FindWalletInfoUseCase {
    WalletInfo fetchWalletInfo(UUID walletId, UUID userId);
}
