package com.example.digital_payment.settlement.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.settlement.domain.models.FailedSettlements;
import com.example.digital_payment.settlement.domain.snapshots.FailedSettlementSnapshot;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.FailedSettlementEntity;

@Component
public class FailedSettlementPersistenceMapper {
    public FailedSettlementEntity toEntity(FailedSettlements failedSettlement) {
        FailedSettlementEntity entity = new FailedSettlementEntity();
        entity.setId(failedSettlement.getId());
        entity.setSettlementId(failedSettlement.getSettlementId());
        entity.setBillId(failedSettlement.getBillId());
        entity.setUserId(failedSettlement.getUserId());
        entity.setCustomerNumber(failedSettlement.getCustomerNumber());
        entity.setAmount(failedSettlement.getAmount());
        entity.setCurrency(failedSettlement.getCurrency());
        entity.setFailureReason(failedSettlement.getFailureReason());
        entity.setAttemptCount(failedSettlement.getAttemptCount());
        entity.setResolutionStatus(failedSettlement.getResolutionStatus());
        entity.setResolutionNotes(failedSettlement.getResolutionNotes());
        entity.setResolvedBy(failedSettlement.getResolvedBy());
        entity.setResolvedAt(failedSettlement.getResolvedAt());
        entity.setCreatedAt(failedSettlement.getCreatedAt());
        return entity;
    }

    public FailedSettlements toDomain(FailedSettlementEntity entity) {
        FailedSettlementSnapshot snapshot = new FailedSettlementSnapshot(entity.getId(),
            entity.getSettlementId(), entity.getBillId(), entity.getUserId(),
            entity.getCustomerNumber(), entity.getAmount(), entity.getCurrency(),
            entity.getFailureReason(), entity.getAttemptCount(), entity.getResolutionStatus(),
            entity.getResolutionNotes(), entity.getResolvedBy(), entity.getResolvedAt(),
            entity.getCreatedAt());
        return FailedSettlements.reconstitute(snapshot);
    }
}