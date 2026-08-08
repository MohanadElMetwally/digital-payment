package com.example.digital_payment.settlement.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementsPort;
import com.example.digital_payment.settlement.application.port.out.SaveFailedSettlementPort;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.FailedSettlementEntity;
import com.example.digital_payment.settlement.infrastructure.persistence.mapper.FailedSettlementPersistenceMapper;
import com.example.digital_payment.settlement.infrastructure.persistence.repository.FailedSettlementJpaRepository;

@Component
public class FailedSettlementPersistenceAdapter
    implements SaveFailedSettlementPort, LoadFailedSettlementPort, LoadFailedSettlementsPort {
    private final FailedSettlementJpaRepository failedSettlementJpaRepository;
    private final FailedSettlementPersistenceMapper failedSettlementPersistenceMapper;

    public FailedSettlementPersistenceAdapter(
        FailedSettlementJpaRepository failedSettlementJpaRepository,
        FailedSettlementPersistenceMapper failedSettlementPersistenceMapper) {
        this.failedSettlementJpaRepository = failedSettlementJpaRepository;
        this.failedSettlementPersistenceMapper = failedSettlementPersistenceMapper;
    }

    @Override
    @Transactional
    public void save(FailedSettlements failedSettlement) {
        FailedSettlementEntity entity = failedSettlementPersistenceMapper
            .toEntity(failedSettlement);
        failedSettlementJpaRepository.save(entity);
    }

    @Override
    public Optional<FailedSettlements> findById(UUID id) {
        return failedSettlementJpaRepository.findById(id)
            .map(failedSettlementPersistenceMapper::toDomain);
    }

    @Override
    public List<FailedSettlements> findAll() {
        return failedSettlementJpaRepository.findAll()
            .stream()
            .map(failedSettlementPersistenceMapper::toDomain)
            .toList();
    }
}