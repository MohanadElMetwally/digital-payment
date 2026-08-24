package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.SavePaymentCustomerPort;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentCustomerEntity;
import com.example.digital_payment.payment.infrastructure.persistence.mapper.PaymentCustomerMapper;
import com.example.digital_payment.payment.infrastructure.persistence.repository.PaymentCustomerJpaRepository;

@Component
public class PaymentCustomerPersistenceAdapter
        implements SavePaymentCustomerPort, LoadPaymentCustomerPort {
    private final PaymentCustomerJpaRepository jpaRepository;
    private final PaymentCustomerMapper mapper;

    public PaymentCustomerPersistenceAdapter(PaymentCustomerJpaRepository jpaRepository,
            PaymentCustomerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PaymentCustomers save(PaymentCustomers customer) {
        PaymentCustomerEntity entity = mapper.toEntity(customer);
        jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Optional<PaymentCustomers> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(mapper::toDomain);
    }

}
