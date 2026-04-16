package com.example.digital_payment.billing.infrastructure.persistence.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.domain.model.entities.Billers;
import com.example.digital_payment.billing.domain.model.snapshots.BillerSnapshot;
import com.example.digital_payment.billing.infrastructure.persistence.entity.BillerEntity;

@Component
public class BillerPersistenceMapper {
    public BillerEntity toEntity(Billers biller) {
        BillerEntity entity = new BillerEntity();
        entity.setId(biller.getId());
        entity.setName(biller.getName());
        entity.setCategory(biller.getCategory());
        entity.setServiceProvider(biller.getServiceProvider());
        entity.setCreatedAt(biller.getCreatedAt());
        return entity;
    }

    public Billers toDomain(BillerEntity entity) {
        BillerSnapshot snapshot = new BillerSnapshot(entity.getId(), entity.getName(),
            entity.getCategory(), entity.getServiceProvider(), entity.getCreatedAt());
        return Billers.reconstitute(snapshot);
    }

    public List<Billers> toDomainList(List<BillerEntity> entities) {
        return entities.stream().map(this::toDomain).toList();
    }
}
