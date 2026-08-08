package com.example.digital_payment.identity.api.dto.response;

import java.util.UUID;

import com.example.digital_payment.identity.application.dto.UserResult;

public record UserResponse(UUID id, String username, String email, String phone, String role,
    String status, String createdAt, ProfileResponse profile) {
    public static UserResponse from(UserResult result) {
        return new UserResponse(result.id(), result.username(), result.email(), result.phone(),
            result.role().toString(), result.status().toString(), result.createdAt().toString(),
            result.profile() != null ? ProfileResponse.from(result.profile()) : null);
    }
}