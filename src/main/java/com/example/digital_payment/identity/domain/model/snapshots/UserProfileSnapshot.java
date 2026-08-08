package com.example.digital_payment.identity.domain.model.snapshots;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileSnapshot(UUID userId, String firstName, String lastName, String country,
    LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
