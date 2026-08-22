package com.example.digital_payment.payment.api.facade;

import java.math.BigDecimal;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionType;

record PaymentInitiationContext(UUID creditCardId, TransactionType type, String currency,
        BigDecimal amount, UUID referenceId, boolean useWallet, UUID walletId) {
    public PaymentInitiationContext(UUID creditCardId, TransactionType type, String currency,
            BigDecimal amount, UUID referenceId) {
        this(creditCardId, type, currency, amount, referenceId, false, null);
    }
}
