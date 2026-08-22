package com.example.digital_payment.payment.domain.model.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.payment.domain.model.snapshots.PaymentCustomerSnapShot;
import com.example.digital_payment.payment.domain.model.valueobjects.PaymentCustomerCreationData;

public class PaymentCustomers {
    private UUID userId;
    private String customerId;
    private LocalDateTime createdAt;

    public PaymentCustomers() {

    }

    public static PaymentCustomers create(PaymentCustomerCreationData creationData) {
        PaymentCustomers customer = new PaymentCustomers();
        customer.userId = creationData.userId();
        customer.customerId = creationData.CustomerId();
        customer.createdAt = LocalDateTime.now();
        return customer;
    }

    public static PaymentCustomers reconstitute(PaymentCustomerSnapShot snapshot) {
        PaymentCustomers customer = new PaymentCustomers();
        customer.userId = snapshot.userId();
        customer.customerId = snapshot.customerId();
        customer.createdAt = snapshot.createdAt();
        return customer;
    }

    public boolean belongsTo(UUID userId) {
        return this.userId == userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
