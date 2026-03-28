package com.example.digital_payment.wallet.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.application.usecase.CreateWalletService;

@Component
public class WalletConfig {
    @Bean
    public CreateWalletUseCase createWalletUseCase(SaveWalletPort saveWalletPort) {
        return new CreateWalletService(saveWalletPort);
    }
}
