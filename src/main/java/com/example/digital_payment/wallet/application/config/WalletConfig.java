package com.example.digital_payment.wallet.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.digital_payment.shared.application.port.in.FindWalletInfoUseCase;
import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.wallet.application.mapper.WalletMapper;
import com.example.digital_payment.wallet.application.port.in.CreateWalletTransactionUseCase;
import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.CreditWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.DebitWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.GetWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.MarkWalletTransactionFailedUseCase;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionByTransactionId;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletTransactionPort;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletPort;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletTransactionPort;
import com.example.digital_payment.wallet.application.usecase.CreateWalletService;
import com.example.digital_payment.wallet.application.usecase.CreateWalletTransactionService;
import com.example.digital_payment.wallet.application.usecase.CreditWalletService;
import com.example.digital_payment.wallet.application.usecase.DebitWalletService;
import com.example.digital_payment.wallet.application.usecase.FindWalletInfoService;
import com.example.digital_payment.wallet.application.usecase.GetWalletService;
import com.example.digital_payment.wallet.application.usecase.MarkWalletTransactionFailedService;

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

    @Bean
    public FindWalletInfoUseCase findTopUpableWalletUseCase(LoadWalletPort loadWalletInfoPort) {
        return new FindWalletInfoService(loadWalletInfoPort);
    }

    @Bean
    public CreditWalletUseCase creditWalletUseCase(TransactionPort transactionPort,
        LoadWalletPort loadWalletPort, UpdateWalletPort updateWalletPort,
        LoadWalletTransactionByTransactionId loadWalletTransactionPort,
        UpdateWalletTransactionPort updateWalletTransactionPort) {
        return new CreditWalletService(transactionPort, loadWalletPort, updateWalletPort,
            loadWalletTransactionPort, updateWalletTransactionPort);
    }

    @Bean
    public DebitWalletUseCase debitWalletUseCase(TransactionPort transactionPort,
        LoadWalletPort loadWalletPort, UpdateWalletPort updateWalletPort,
        LoadWalletTransactionByTransactionId loadWalletTransactionPort,
        UpdateWalletTransactionPort updateWalletTransactionPort) {
        return new DebitWalletService(transactionPort, loadWalletPort, updateWalletPort,
            loadWalletTransactionPort, updateWalletTransactionPort);
    }

    @Bean
    public CreateWalletTransactionUseCase createWalletTransactionUseCase(
        TransactionPort transactionPort, LoadWalletPort loadWalletPort,
        SaveWalletTransactionPort saveWalletTransactionPort) {
        return new CreateWalletTransactionService(transactionPort, loadWalletPort,
            saveWalletTransactionPort);
    }

    @Bean
    public MarkWalletTransactionFailedUseCase markWalletTransactionFailedUseCase(
        TransactionPort transactionPort,
        LoadWalletTransactionByTransactionId loadWalletTransactionPort,
        UpdateWalletTransactionPort updateWalletTransactionPort) {
        return new MarkWalletTransactionFailedService(transactionPort, loadWalletTransactionPort,
            updateWalletTransactionPort);
    }
}
