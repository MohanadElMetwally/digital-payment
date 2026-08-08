package com.example.digital_payment.payment.application.dto;

import java.util.UUID;

public record PaymentCustomerCreateCommand(UUID userId, String email, String fullName) {

}
