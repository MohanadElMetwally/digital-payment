package com.example.digital_payment.billing.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.billing.domain.model.snapshots.BillSnapshot;
import com.example.digital_payment.billing.infrastructure.persistence.entity.BillEntity;
import com.example.digital_payment.shared.dto.BillInfo;

@Component
public class BillPersistenceMapper {
    public BillEntity toEntity(Bills bill) {
        BillEntity entity = new BillEntity();
        entity.setId(bill.getId());
        entity.setBillerId(bill.getBillerId());
        entity.setUserId(bill.getUserId());
        entity.setExternalCustomerNumber(bill.getExternalCustomerNumber());
        entity.setExternalCustomerName(bill.getExternalCustomerName());
        entity.setExternalBillId(bill.getExternalBillId());
        entity.setAmount(bill.getAmount());
        entity.setCurrency(bill.getCurrency());
        entity.setStatus(bill.getStatus());
        entity.setBillingPeriodStart(bill.getBillingPeriodStart());
        entity.setBillingPeriodEnd(bill.getBillingPeriodEnd());
        entity.setDueDate(bill.getDueDate());
        entity.setLastSyncedAt(bill.getLastSyncedAt());
        entity.setCreatedAt(bill.getCreatedAt());
        return entity;
    }

    public Bills toDomain(BillEntity entity) {
        BillSnapshot snapshot = new BillSnapshot(entity.getId(), entity.getBillerId(),
            entity.getUserId(), entity.getExternalCustomerNumber(),
            entity.getExternalCustomerName(), entity.getExternalBillId(), entity.getAmount(),
            entity.getCurrency(), entity.getStatus(), entity.getBillingPeriodStart(),
            entity.getBillingPeriodEnd(), entity.getDueDate(), entity.getLastSyncedAt(),
            entity.getCreatedAt());
        return Bills.reconstitute(snapshot);
    }

    public BillEntity syncBill(Bills bill, BillEntity entity) {
        if (bill.isResyncable()) {
            entity.setAmount(bill.getAmount());
            entity.setLastSyncedAt(bill.getLastSyncedAt());
        }
        return entity;
    }

    public BillInfo toInfo(BillEntity entity) {
        return new BillInfo(entity.getCurrency(), entity.getAmount());
    }

    public void update(Bills bill, BillEntity entity) {
        entity.setStatus(bill.getStatus());
    }
}
