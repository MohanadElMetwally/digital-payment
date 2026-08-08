package com.example.digital_payment.fake_provider.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.digital_payment.fake_provider.application.mapper.FakeBillMapper;
import com.example.digital_payment.fake_provider.application.port.in.GetFakeBillUseCase;
import com.example.digital_payment.fake_provider.application.port.in.MarkFakeBillPaidUseCase;
import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.application.port.out.UpdateFakeBillPort;
import com.example.digital_payment.fake_provider.application.usecase.GetFakeBillService;
import com.example.digital_payment.fake_provider.application.usecase.MarkFakeBillPaidService;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class FakeBillConfig {
    @Bean
    public FakeBillMapper fakeBillMapper() {
        return new FakeBillMapper();
    }

    @Bean
    public GetFakeBillUseCase getFakeBillUseCase(LoadFakeBillPort loadFakeBillPort,
        FakeBillMapper fakeBillMapper) {
        return new GetFakeBillService(loadFakeBillPort, fakeBillMapper);
    }

    @Bean
    public MarkFakeBillPaidUseCase markFakeBillPaidUseCase(TransactionPort transaction,
        LoadFakeBillPort loadFakeBillPort, UpdateFakeBillPort updateFakeBillPort) {
        return new MarkFakeBillPaidService(transaction, loadFakeBillPort, updateFakeBillPort);
    }
}
