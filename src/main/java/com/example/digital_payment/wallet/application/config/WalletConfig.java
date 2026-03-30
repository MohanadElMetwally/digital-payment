package com.example.digital_payment.wallet.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.application.mapper.WalletMapper;
import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.GetWalletUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.application.usecase.CreateWalletService;
import com.example.digital_payment.wallet.application.usecase.GetWalletService;

@Component
public class WalletConfig {
    @Bean
    public CreateWalletUseCase createWalletUseCase(SaveWalletPort saveWalletPort) {
        return new CreateWalletService(saveWalletPort);
    }

    @Bean
    public GetWalletUseCase getWalletUseCase(LoadWalletPort loadWalletPort,
        WalletMapper walletMapper) {
        return new GetWalletService(loadWalletPort, walletMapper);
    }

    @Bean
    public WalletMapper walletMapper() {
        return new WalletMapper();
    }
}
