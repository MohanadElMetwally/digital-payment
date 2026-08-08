package com.example.digital_payment.billing.infrastructure.persistence.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.digital_payment.billing.application.port.out.LoadBillByExternalRefPort;
import com.example.digital_payment.billing.application.port.out.LoadBillPort;
import com.example.digital_payment.billing.application.port.out.SaveBillPort;
import com.example.digital_payment.billing.application.port.out.SyncBillPort;
import com.example.digital_payment.billing.application.port.out.UpdateBillPort;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.billing.infrastructure.persistence.entity.BillEntity;
import com.example.digital_payment.billing.infrastructure.persistence.mappers.BillPersistenceMapper;
import com.example.digital_payment.billing.infrastructure.persistence.repository.BillJpaRepository;

@Component
public class BillPersistenceAdapter
    implements SaveBillPort, LoadBillPort, LoadBillByExternalRefPort, SyncBillPort, UpdateBillPort {
    private final BillJpaRepository billJpaRepository;
    private final BillPersistenceMapper billPersistenceMapper;

    public BillPersistenceAdapter(BillJpaRepository billJpaRepository,
        BillPersistenceMapper billPersistenceMapper) {
        this.billJpaRepository = billJpaRepository;
        this.billPersistenceMapper = billPersistenceMapper;
    }

    @Override
    @Transactional
    public Bills save(Bills bill) {
        BillEntity entity = billPersistenceMapper.toEntity(bill);
        billJpaRepository.save(entity);
        return billPersistenceMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Optional<Bills> findById(UUID id) {
        return billJpaRepository.findById(id).map(billPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public Optional<Bills> findByExternalBillId(String externalBillId) {
        return billJpaRepository.findByExternalBillId(externalBillId)
            .map(billPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public Optional<Bills> findByExternalBillIdAndBillerId(String externalBillId, UUID billerId) {
        return billJpaRepository.findByExternalBillIdAndBillerId(externalBillId, billerId)
            .map(billPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public Bills sync(Bills bill) {
        BillEntity entity = billJpaRepository.getReferenceById(bill.getId());
        billPersistenceMapper.syncBill(bill, entity);
        return billPersistenceMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void update(Bills bill) {
        BillEntity entity = billJpaRepository.getReferenceById(bill.getId());
        billPersistenceMapper.update(bill, entity);
    }
}
