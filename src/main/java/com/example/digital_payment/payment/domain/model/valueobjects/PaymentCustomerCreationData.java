package com.example.digital_payment.payment.domain.model.valueobjects;

import java.util.UUID;

public record PaymentCustomerCreationData(UUID userId, String CustomerId) {

}
