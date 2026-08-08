package com.example.digital_payment.identity.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdatePasswordRequest(
    @NotNull
    String currentPassword, 
    @NotNull
    String newPassword,
    @NotNull
    String confirmNewPassword
) { }
