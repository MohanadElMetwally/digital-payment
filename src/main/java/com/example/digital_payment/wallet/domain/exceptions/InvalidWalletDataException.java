package com.example.digital_payment.wallet.domain.exceptions;

public class InvalidWalletDataException extends RuntimeException {
    public InvalidWalletDataException(String msg) {
        super(msg);
    }
}
