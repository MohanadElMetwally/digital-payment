package com.example.digital_payment.identity.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be 3-30 characters")
    String username,

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address")
    String email,

    @NotNull(message = "Phone is required")
    String phone,

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password
) {}