package com.example.digital_payment.fake_provider.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.digital_payment.fake_provider.application.mapper.FakeBillMapper;
import com.example.digital_payment.fake_provider.application.port.in.GetFakeBillUseCase;
import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.application.usecase.GetFakeBillService;

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
}
