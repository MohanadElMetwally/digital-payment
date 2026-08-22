package com.example.digital_payment.settlement.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.settlement.application.port.out.ClaimSettlementPort;
import com.example.digital_payment.settlement.application.port.out.EnqueueSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsPort;
import com.example.digital_payment.settlement.application.port.out.SaveSettlementPort;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementPort;
import com.example.digital_payment.settlement.domain.models.Settlements;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementEntity;
import com.example.digital_payment.settlement.infrastructure.persistence.mapper.SettlementPersistenceMapper;
import com.example.digital_payment.settlement.infrastructure.persistence.repository.SettlementJpaRepository;

@Component
public class SettlementPersistenceAdapter implements SaveSettlementPort, LoadSettlementPort,
        LoadSettlementsPort, UpdateSettlementPort, ClaimSettlementPort, EnqueueSettlementPort {
    private final SettlementJpaRepository settlementJpaRepository;
    private final SettlementPersistenceMapper settlementPersistenceMapper;

    public SettlementPersistenceAdapter(SettlementJpaRepository settlementJpaRepository,
            SettlementPersistenceMapper settlementPersistenceMapper) {
        this.settlementJpaRepository = settlementJpaRepository;
        this.settlementPersistenceMapper = settlementPersistenceMapper;
    }

    @Override
    @Transactional
    public void save(Settlements settlement) {
        SettlementEntity entity = settlementPersistenceMapper.toEntity(settlement);
        settlementJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<Settlements> findById(UUID billId) {
        return settlementJpaRepository.findById(billId).map(settlementPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public List<Settlements> findAll() {
        return settlementJpaRepository.findAll().stream().map(settlementPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void update(Settlements settlement) {
        SettlementEntity entity = settlementJpaRepository.getReferenceById(settlement.getId());
        settlementPersistenceMapper.update(settlement, entity);
    }

    @Override
    @Transactional
    public boolean claim(UUID id) {
        return settlementJpaRepository.claimForProcessing(id, LocalDateTime.now()) == 1;
    }

    @Override
    @Transactional
    public int enqueue(int batchSize) {
        return settlementJpaRepository.claimAndEnqueueBatch(LocalDateTime.now(), batchSize);
    }
}
