package com.example.digital_payment.billing.application.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.digital_payment.billing.application.mapper.BillMapper;
import com.example.digital_payment.billing.application.mapper.BillerMapper;
import com.example.digital_payment.billing.application.port.in.BillFetchUseCase;
import com.example.digital_payment.billing.application.port.in.GetAllBillersUseCase;
import com.example.digital_payment.billing.application.port.in.GetBillerUseCase;
import com.example.digital_payment.billing.application.port.out.FindPayableBillPort;
import com.example.digital_payment.billing.application.port.out.LoadAllBillersPort;
import com.example.digital_payment.billing.application.port.out.LoadBillByExternalRefPort;
import com.example.digital_payment.billing.application.port.out.LoadBillerPort;
import com.example.digital_payment.billing.application.port.out.ProviderGatewayPort;
import com.example.digital_payment.billing.application.port.out.SaveBillPort;
import com.example.digital_payment.billing.application.port.out.SyncBillPort;
import com.example.digital_payment.billing.application.usecase.BillFetchService;
import com.example.digital_payment.billing.application.usecase.FindPayableBillService;
import com.example.digital_payment.billing.application.usecase.GetAllBillersService;
import com.example.digital_payment.billing.application.usecase.GetBillerService;
import com.example.digital_payment.billing.infrastructure.persistence.mappers.ProviderBillMapper;
import com.example.digital_payment.shared.application.port.in.FindPayableBillUseCase;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class BillingConfig {
    @Bean
    public GetBillerUseCase getBillerUseCase(LoadBillerPort loadBillerPort,
        BillerMapper billerMapper) {
        return new GetBillerService(loadBillerPort, billerMapper);
    }

    @Bean
    public GetAllBillersUseCase getAllBillersUseCase(LoadAllBillersPort loadAllBillersPort,
        BillerMapper billerMapper) {
        return new GetAllBillersService(loadAllBillersPort, billerMapper);
    }

    @Bean
    public BillerMapper billerMapper() {
        return new BillerMapper();
    }

    @Bean
    public BillMapper billMapper() {
        return new BillMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ProviderBillMapper providerBillMapper() {
        return new ProviderBillMapper();
    }

    @Bean
    public BillFetchUseCase billFetchUseCase(LoadBillerPort loadBillerPort,
        LoadBillByExternalRefPort loadBillByExternalRefPort, SaveBillPort saveBillPort,
        SyncBillPort syncBillPort, TransactionPort transactionPort,
        Map<String, ProviderGatewayPort> providers, BillMapper billMapper) {
        return new BillFetchService(loadBillerPort, loadBillByExternalRefPort, saveBillPort,
            syncBillPort, transactionPort, providers, billMapper);
    }

    @Bean
    public FindPayableBillUseCase checkBillExistUseCase(FindPayableBillPort findPayableBillPort) {
        return new FindPayableBillService(findPayableBillPort);
    }
}
