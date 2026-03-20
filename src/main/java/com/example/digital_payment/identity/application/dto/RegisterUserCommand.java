package com.example.digital_payment.identity.application.dto;

public record RegisterUserCommand(String username, String email, String phone, String password) {
}