package com.example.digital_payment.identity.application.dto;

import java.time.LocalDate;

public record ProfileResult(String firstName, String lastName, String country,
        LocalDate dateOfBirth) {
}
