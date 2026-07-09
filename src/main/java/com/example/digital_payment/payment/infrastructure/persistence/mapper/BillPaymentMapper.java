package com.example.digital_payment.payment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.domain.model.entities.BillPayments;
import com.example.digital_payment.payment.domain.model.snapshots.BillPaymentSnapshot;
import com.example.digital_payment.payment.infrastructure.persistence.entity.BillPaymentEntity;

@Component
public class BillPaymentMapper {
    public BillPayments toDomain(BillPaymentEntity entity) {
        BillPaymentSnapshot snapshot = new BillPaymentSnapshot(entity.getId(),
            entity.getTransactionId(), entity.getBillId(), entity.getAmount(), entity.getStatus(),
            entity.getPaidAt());
        return BillPayments.reconstitute(snapshot);
    }

    public BillPaymentEntity toEntity(BillPayments billPayment) {
        BillPaymentEntity entity = new BillPaymentEntity();
        entity.setId(billPayment.getId());
        entity.setTransactionId(billPayment.getTransactionId());
        entity.setBillId(billPayment.getBillId());
        entity.setAmount(billPayment.getAmount());
        entity.setStatus(billPayment.getStatus());
        entity.setPaidAt(billPayment.getPaidAt());
        return entity;
    }

    public BillPaymentEntity updateEntity(BillPayments billPayment, BillPaymentEntity entity) {
        entity.setStatus(billPayment.getStatus());
        entity.setPaidAt(billPayment.getPaidAt());
        return entity;
    }
}
