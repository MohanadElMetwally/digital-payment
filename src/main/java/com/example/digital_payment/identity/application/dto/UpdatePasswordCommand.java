package com.example.digital_payment.identity.application.dto;

public record UpdatePasswordCommand(String currentPassword, String newPassword,
        String confirmNewPassword) {

}
