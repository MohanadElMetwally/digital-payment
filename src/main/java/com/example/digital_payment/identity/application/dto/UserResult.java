package com.example.digital_payment.identity.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;

public record UserResult(UUID id, String username, String email, String phone, UserRole role,
                UserStatus status, LocalDateTime createdAt, ProfileResult profile) {
}
