package com.example.digital_payment.payment.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.CardBrand;
import com.example.digital_payment.payment.domain.enums.CardStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {
    @Id
    private UUID id;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private CardBrand brand;
    private String lastFour;
    private String expiryMonth;
    private String expiryYear;
    private String paymentMethodId;
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private Boolean isDefault;
    private LocalDateTime createdAt;
}
