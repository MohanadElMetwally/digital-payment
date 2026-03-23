package com.example.digital_payment.identity.domain.model.valueobjects;

import java.time.LocalDate;

import com.example.digital_payment.identity.domain.enums.UserRole;

public record UserRegistrationData(String username, String email, String phone, String password,
    UserRole role, String firstName, String lastName, String country, LocalDate dateOfBirth) {

}
