package com.example.digital_payment.identity.domain.model.snapshots;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;

public record UserSnapshot(UUID id, String username, String email, String phone, String password,
        UserRole role, UserStatus status, LocalDateTime createdAt, LocalDateTime updatedAt,
        UserProfileSnapshot profile) {

}
