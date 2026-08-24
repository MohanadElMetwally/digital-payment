package com.example.digital_payment.settlement.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsOutboxPort;
import com.example.digital_payment.settlement.application.port.out.LockSettlementOutboxPort;
import com.example.digital_payment.settlement.application.port.out.SaveSettlementsOutboxPort;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementsOutboxPort;
import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementOutboxEntity;
import com.example.digital_payment.settlement.infrastructure.persistence.mapper.SettlementOutboxPersistenceMapper;
import com.example.digital_payment.settlement.infrastructure.persistence.repository.SettlementOutboxJpaRepository;

@Component
public class SettlementOutboxPersistenceAdapter implements SaveSettlementsOutboxPort,
        LoadSettlementsOutboxPort, UpdateSettlementsOutboxPort, LockSettlementOutboxPort {
    private final SettlementOutboxJpaRepository jpaRepository;
    private final SettlementOutboxPersistenceMapper mapper;

    public SettlementOutboxPersistenceAdapter(SettlementOutboxJpaRepository jpaRepository,
            SettlementOutboxPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(SettlementsOutbox outbox) {
        SettlementOutboxEntity entity = mapper.toEntity(outbox);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SettlementsOutbox> findBySettlementId(UUID settlementId) {
        return jpaRepository.findBySettlementId(settlementId).map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void update(SettlementsOutbox outbox) {
        SettlementOutboxEntity entity = jpaRepository.getReferenceById(outbox.getId());
        mapper.update(outbox, entity);
    }

    @Override
    @Transactional
    public List<SettlementsOutbox> lockPendingBatch(int batchSize) {
        return jpaRepository.lockPendingBatch(batchSize).stream().map(mapper::toDomain).toList();
    }
}
