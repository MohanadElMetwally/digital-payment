package com.example.digital_payment.wallet.domain.exceptions;

import com.example.digital_payment.shared.exception.ForbiddenException;

public class WalletAccessDenied extends ForbiddenException {
    public WalletAccessDenied() {
        super("Access to wallet denied");
    }
}
