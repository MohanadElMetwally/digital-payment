package com.example.digital_payment.identity.domain.model.valueobjects;

import java.time.LocalDate;
import java.util.UUID;

public record UserProfileRegistrationData(UUID userId, String firstName, String lastName,
    String country, LocalDate dateOfBirth) {

}