package com.example.digital_payment.wallet.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.wallet.domain.model.entities.Wallets;

public interface LoadWalletPort {
    Optional<Wallets> getByUserId(UUID userId);

}
