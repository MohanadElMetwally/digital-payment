package com.example.digital_payment.billing.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.example.digital_payment.billing.application.port.out.LoadAllBillersPort;
import com.example.digital_payment.billing.application.port.out.LoadBillerPort;
import com.example.digital_payment.billing.domain.model.entities.Billers;
import com.example.digital_payment.billing.infrastructure.persistence.entity.BillerEntity;
import com.example.digital_payment.billing.infrastructure.persistence.mappers.BillerPersistenceMapper;
import com.example.digital_payment.billing.infrastructure.persistence.repository.BillerJpaRepository;

@Component
public class BillerPersistenceAdapter implements LoadBillerPort, LoadAllBillersPort {
    private final BillerJpaRepository billerJpaRepository;
    private final BillerPersistenceMapper billerPersistenceMapper;

    public BillerPersistenceAdapter(BillerJpaRepository billerJpaRepository,
            BillerPersistenceMapper billerPersistenceMapper) {
        this.billerJpaRepository = billerJpaRepository;
        this.billerPersistenceMapper = billerPersistenceMapper;
    }

    @Override
    public List<Billers> findAll() {
        List<BillerEntity> entities = billerJpaRepository.findAll();
        return billerPersistenceMapper.toDomainList(entities);
    }

    @Override
    public Optional<Billers> findById(UUID id) {
        return billerJpaRepository.findById(id).map(billerPersistenceMapper::toDomain);
    }

}
