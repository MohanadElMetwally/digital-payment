package com.example.digital_payment.wallet.domain.exceptions;

import java.util.UUID;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class WalletNotFoundException extends ResourceNotFoundException {
    public WalletNotFoundException(UUID id) {
        super("Wallet not found for user ID: %s.".formatted(id));
    }
}
