package com.example.digital_payment.settlement.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.digital_payment.settlement.application.port.in.EnqueueSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.in.GetFailedSettlementUseCase;
import com.example.digital_payment.settlement.application.port.in.GetFailedSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.in.GetSettlementUseCase;
import com.example.digital_payment.settlement.application.port.in.GetSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.in.ProcessSettlementUseCase;
import com.example.digital_payment.settlement.application.port.in.RelaySettlementOutboxUseCase;
import com.example.digital_payment.settlement.application.port.in.SaveFailedSettlementUseCase;
import com.example.digital_payment.settlement.application.port.in.SaveSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.ClaimSettlementPort;
import com.example.digital_payment.settlement.application.port.out.EnqueueSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementsPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsOutboxPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsPort;
import com.example.digital_payment.settlement.application.port.out.LockSettlementOutboxPort;
import com.example.digital_payment.settlement.application.port.out.PayProviderGateway;
import com.example.digital_payment.settlement.application.port.out.SaveFailedSettlementPort;
import com.example.digital_payment.settlement.application.port.out.SaveSettlementPort;
import com.example.digital_payment.settlement.application.port.out.SettlementPublisherPort;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementPort;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementsOutboxPort;
import com.example.digital_payment.settlement.application.usecase.EnqueueSettlementsService;
import com.example.digital_payment.settlement.application.usecase.GetFailedSettlementService;
import com.example.digital_payment.settlement.application.usecase.GetFailedSettlementsService;
import com.example.digital_payment.settlement.application.usecase.GetSettlementService;
import com.example.digital_payment.settlement.application.usecase.GetSettlementsService;
import com.example.digital_payment.settlement.application.usecase.ProcessSettlementService;
import com.example.digital_payment.settlement.application.usecase.RelaySettlementOutboxService;
import com.example.digital_payment.settlement.application.usecase.SaveFailedSettlementService;
import com.example.digital_payment.settlement.application.usecase.SaveSettlementService;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class SettlementsConfig {
    @Bean
    public SaveSettlementUseCase saveSettlementUseCase(SaveSettlementPort saveSettlementPort) {
        return new SaveSettlementService(saveSettlementPort);
    }

    @Bean
    public SaveFailedSettlementUseCase saveFailedSettlementUseCase(
            SaveFailedSettlementPort saveFailedSettlementPort,
            LoadSettlementPort loadSettlementPort) {
        return new SaveFailedSettlementService(saveFailedSettlementPort, loadSettlementPort);
    }

    @Bean
    public GetSettlementUseCase getSettlementUseCase(LoadSettlementPort loadSettlementPort) {
        return new GetSettlementService(loadSettlementPort);
    }

    @Bean
    public GetFailedSettlementUseCase getFailedSettlementUseCase(
            LoadFailedSettlementPort loadFailedSettlementPort) {
        return new GetFailedSettlementService(loadFailedSettlementPort);
    }

    @Bean
    public GetFailedSettlementsUseCase getFailedSettlementsUseCase(
            LoadFailedSettlementsPort loadFailedSettlementsPort) {
        return new GetFailedSettlementsService(loadFailedSettlementsPort);
    }

    @Bean
    public GetSettlementsUseCase getSettlementsUseCase(LoadSettlementsPort loadSettlementsPort) {
        return new GetSettlementsService(loadSettlementsPort);
    }

    @Bean
    public ProcessSettlementUseCase processSettlementUseCase(LoadSettlementPort loadSettlementPort,
            PayProviderGateway payProviderGateway, UpdateSettlementPort updateSettlementPort,
            TransactionPort transactionPort, ClaimSettlementPort claimSettlementPort) {
        return new ProcessSettlementService(loadSettlementPort, payProviderGateway,
                updateSettlementPort, transactionPort, claimSettlementPort);
    }

    @Bean
    public EnqueueSettlementsUseCase enqueueSettlementsUseCase(
            EnqueueSettlementPort enqueueSettlementPort) {
        return new EnqueueSettlementsService(enqueueSettlementPort);
    }

    @Bean
    public RelaySettlementOutboxUseCase relaySettlementOutboxUseCase(
            LockSettlementOutboxPort lockSettlementOutboxPort, SettlementPublisherPort publisher,
            TransactionPort transactionPort, LoadSettlementsOutboxPort loadSettlementsOutboxPort,
            UpdateSettlementsOutboxPort updateSettlementsOutboxPort) {
        return new RelaySettlementOutboxService(lockSettlementOutboxPort, publisher,
                transactionPort, loadSettlementsOutboxPort, updateSettlementsOutboxPort);
    }

}
