package com.example.digital_payment.wallet.domain.exceptions;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class WalletTransactionNotFoundException extends ResourceNotFoundException {
    public WalletTransactionNotFoundException() {
        super("Wallet transaction not found");
    }
}
