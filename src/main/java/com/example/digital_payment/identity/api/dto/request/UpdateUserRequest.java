package com.example.digital_payment.identity.api.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UpdateUserRequest(@Nullable @Email(message = "Email must be valid") String email,

        @Nullable String phone) {
}
