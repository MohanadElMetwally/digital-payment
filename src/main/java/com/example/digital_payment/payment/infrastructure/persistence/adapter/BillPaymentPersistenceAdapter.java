package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.digital_payment.payment.application.port.out.LoadBillPaymentByTransactionIdPort;
import com.example.digital_payment.payment.application.port.out.SaveBillPaymentPort;
import com.example.digital_payment.payment.application.port.out.UpdateBillPaymentPort;
import com.example.digital_payment.payment.domain.model.entities.BillPayments;
import com.example.digital_payment.payment.infrastructure.persistence.entity.BillPaymentEntity;
import com.example.digital_payment.payment.infrastructure.persistence.mapper.BillPaymentMapper;
import com.example.digital_payment.payment.infrastructure.persistence.repository.BillPaymentJpaRepository;

@Component
public class BillPaymentPersistenceAdapter
    implements SaveBillPaymentPort, UpdateBillPaymentPort, LoadBillPaymentByTransactionIdPort {
    private final BillPaymentJpaRepository jpaRepository;
    private final BillPaymentMapper mapper;

    public BillPaymentPersistenceAdapter(BillPaymentJpaRepository jpaRepository,
        BillPaymentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(BillPayments billPayment) {
        BillPaymentEntity entity = mapper.toEntity(billPayment);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(BillPayments billPayment) {
        BillPaymentEntity entity = jpaRepository.getReferenceById(billPayment.getId());
        mapper.updateEntity(billPayment, entity);
    }

    @Override
    public Optional<BillPayments> findByTransactionId(UUID transactionId) {
        return jpaRepository.findByTransactionId(transactionId).map(mapper::toDomain);
    }
}
