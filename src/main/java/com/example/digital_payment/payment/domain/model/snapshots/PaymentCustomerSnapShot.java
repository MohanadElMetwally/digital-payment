package com.example.digital_payment.payment.domain.model.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentCustomerSnapShot(UUID userId, String customerId, LocalDateTime createdAt) {

}
