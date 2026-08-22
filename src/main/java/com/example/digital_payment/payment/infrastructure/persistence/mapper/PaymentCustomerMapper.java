package com.example.digital_payment.payment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.snapshots.PaymentCustomerSnapShot;
import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentCustomerEntity;

@Component
public class PaymentCustomerMapper {
    public PaymentCustomerEntity toEntity(PaymentCustomers customer) {
        PaymentCustomerEntity entity = new PaymentCustomerEntity();
        entity.setUserId(customer.getUserId());
        entity.setCustomerId(customer.getCustomerId());
        entity.setCreatedAt(customer.getCreatedAt());
        return entity;
    }

    public PaymentCustomers toDomain(PaymentCustomerEntity entity) {
        PaymentCustomerSnapShot snapshot = new PaymentCustomerSnapShot(entity.getUserId(),
                entity.getCustomerId(), entity.getCreatedAt());
        return PaymentCustomers.reconstitute(snapshot);
    }
}
