package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.port.out.WalletDebitPort;

@Component
public class WalletDebitAdapter implements WalletDebitPort {

    @Override
    public void debitWallet(UUID userId, BigDecimal amount) {
        throw new UnsupportedOperationException("Unimplemented method 'debitWallet'");
    }
    
}
