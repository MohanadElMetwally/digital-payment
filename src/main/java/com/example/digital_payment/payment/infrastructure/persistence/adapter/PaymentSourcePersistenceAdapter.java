package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.payment.application.port.out.SavePaymentSourcePort;
import com.example.digital_payment.payment.domain.model.entities.PaymentSources;
import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentSourceEntity;
import com.example.digital_payment.payment.infrastructure.persistence.mapper.PaymentSourcePersistenceMapper;
import com.example.digital_payment.payment.infrastructure.persistence.repository.PaymentSourceJpaRepository;

@Component
public class PaymentSourcePersistenceAdapter implements SavePaymentSourcePort {
    private final PaymentSourceJpaRepository paymentSourceJpaRepository;
    private final PaymentSourcePersistenceMapper mapper;

    public PaymentSourcePersistenceAdapter(PaymentSourceJpaRepository paymentSourceJpaRepository,
            PaymentSourcePersistenceMapper mapper) {
        this.paymentSourceJpaRepository = paymentSourceJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(PaymentSources paymentSource) {
        PaymentSourceEntity entity = mapper.toEntity(paymentSource);
        paymentSourceJpaRepository.save(entity);
    }
}
