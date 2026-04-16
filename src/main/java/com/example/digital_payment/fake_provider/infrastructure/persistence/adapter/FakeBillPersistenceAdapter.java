package com.example.digital_payment.fake_provider.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;
import com.example.digital_payment.fake_provider.infrastructure.persistence.mapper.FakeBillPersistenceMapper;
import com.example.digital_payment.fake_provider.infrastructure.persistence.repository.FakeBillJpaRepository;

@Component
public class FakeBillPersistenceAdapter implements LoadFakeBillPort {
    private final FakeBillPersistenceMapper fakeBillPersistenceMapper;
    private final FakeBillJpaRepository fakeBillJpaRepository;

    public FakeBillPersistenceAdapter(FakeBillPersistenceMapper fakeBillPersistenceMapper,
        FakeBillJpaRepository fakeBillJpaRepository) {
        this.fakeBillPersistenceMapper = fakeBillPersistenceMapper;
        this.fakeBillJpaRepository = fakeBillJpaRepository;
    }

    @Override
    public Optional<FakeBills> findByCustomerNumber(String customerNumber) {
        return fakeBillJpaRepository.findByCustomerNumber(customerNumber)
            .map(fakeBillPersistenceMapper::toDomain);
    }

}
