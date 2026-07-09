package com.example.digital_payment.payment.application.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletDebitPort {
    void debitWallet(UUID userId, BigDecimal amount);
}
