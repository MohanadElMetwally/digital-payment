package com.example.digital_payment.payment.domain.model.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.CardBrand;
import com.example.digital_payment.payment.domain.enums.CardStatus;
import com.example.digital_payment.payment.domain.model.snapshots.CreditCardSnapshot;
import com.example.digital_payment.payment.domain.model.valueobjects.CardRegistrationCreationData;

public class CreditCards {
    private UUID id;
    private UUID userId;
    private CardBrand brand;
    private String lastFour;
    private String expiryMonth;
    private String expiryYear;
    private String paymentMethodId;
    private CardStatus status;
    private Boolean isDefault;
    private LocalDateTime createdAt;

    public CreditCards() {

    }

    public static CreditCards create(CardRegistrationCreationData creationData) {
        CreditCards card = new CreditCards();
        card.id = UUID.randomUUID();
        card.userId = creationData.userId();
        card.brand = creationData.brand();
        card.lastFour = creationData.lastFour();
        card.expiryMonth = creationData.expiryMonth();
        card.expiryYear = creationData.expiryYear();
        card.paymentMethodId = creationData.paymentMethodId();
        card.status = creationData.status();
        card.isDefault = creationData.isDefault();
        card.createdAt = LocalDateTime.now();
        return card;
    }

    public static CreditCards reconstitute(CreditCardSnapshot snapshot) {
        CreditCards card = new CreditCards();
        card.id = snapshot.id();
        card.userId = snapshot.userId();
        card.brand = snapshot.brand();
        card.lastFour = snapshot.lastFour();
        card.expiryMonth = snapshot.expiryMonth();
        card.expiryYear = snapshot.expiryYear();
        card.paymentMethodId = snapshot.paymentMethodId();
        card.status = snapshot.status();
        card.isDefault = snapshot.isDefault();
        card.createdAt = snapshot.createdAt();
        return card;
    }

    public void markDefault() {
        this.isDefault = true;
    }

    public void expire() {
        this.status = CardStatus.EXPIRED;
    }

    public void disable() {
        this.status = CardStatus.DISABLED;
    }

    public boolean belongsTo(UUID userId) {
        return this.getUserId().equals(userId);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public CardBrand getBrand() {
        return brand;
    }

    public String getLastFour() {
        return lastFour;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public CardStatus getStatus() {
        return status;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
