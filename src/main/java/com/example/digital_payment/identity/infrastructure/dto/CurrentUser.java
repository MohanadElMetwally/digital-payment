package com.example.digital_payment.identity.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;

public record CurrentUser(UUID id, String username, String password, String phone, UserRole role,
    UserStatus userStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
