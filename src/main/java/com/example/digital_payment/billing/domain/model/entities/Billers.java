package com.example.digital_payment.billing.domain.model.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillCategory;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;
import com.example.digital_payment.billing.domain.model.snapshots.BillerSnapshot;
import com.example.digital_payment.billing.domain.model.valueobjects.BillerCreateData;

public class Billers {
    private UUID id;
    private String name;
    private BillCategory category;
    private ServiceProvider serviceProvider;
    private LocalDateTime createdAt;

    public Billers() {}

    public static Billers create(BillerCreateData createData) {
        validateServiceProvider(createData.serviceProvider());
        validateBillCategory(createData.category());

        Billers biller = new Billers();
        biller.id = UUID.randomUUID();
        biller.name = createData.name();
        biller.category = createData.category();
        biller.serviceProvider = createData.serviceProvider();
        biller.createdAt = LocalDateTime.now();
        return biller;
    }

    public static Billers reconstitute(BillerSnapshot snapshot) {
        Billers biller = new Billers();
        biller.id = snapshot.id();
        biller.name = snapshot.name();
        biller.category = snapshot.category();
        biller.serviceProvider = snapshot.serviceProvider();
        biller.createdAt = snapshot.createdAt();
        return biller;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BillCategory getCategory() {
        return category;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private static void validateServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProvider == null) {
            throw new IllegalArgumentException("Service provider cannot be null");
        }
    }

    private static void validateBillCategory(BillCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Bill category cannot be null");
        }
    }
}
