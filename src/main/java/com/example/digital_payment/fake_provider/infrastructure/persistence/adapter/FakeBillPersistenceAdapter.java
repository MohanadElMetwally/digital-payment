package com.example.digital_payment.fake_provider.infrastructure.persistence.adapter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.application.port.out.UpdateFakeBillPort;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;
import com.example.digital_payment.fake_provider.infrastructure.persistence.entity.FakeBillEntity;
import com.example.digital_payment.fake_provider.infrastructure.persistence.mapper.FakeBillPersistenceMapper;
import com.example.digital_payment.fake_provider.infrastructure.persistence.repository.FakeBillJpaRepository;

@Component
public class FakeBillPersistenceAdapter implements LoadFakeBillPort, UpdateFakeBillPort {
    private final FakeBillPersistenceMapper fakeBillPersistenceMapper;
    private final FakeBillJpaRepository fakeBillJpaRepository;

    public FakeBillPersistenceAdapter(FakeBillPersistenceMapper fakeBillPersistenceMapper,
            FakeBillJpaRepository fakeBillJpaRepository) {
        this.fakeBillPersistenceMapper = fakeBillPersistenceMapper;
        this.fakeBillJpaRepository = fakeBillJpaRepository;
    }

    @Override
    @Transactional
    public Optional<FakeBills> findByCustomerNumber(String customerNumber) {
        return fakeBillJpaRepository.findByCustomerNumber(customerNumber)
                .map(fakeBillPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public void update(FakeBills fakeBill) {
        FakeBillEntity entity = fakeBillJpaRepository.getReferenceById(fakeBill.getId());
        fakeBillPersistenceMapper.update(fakeBill, entity);
    }

}
