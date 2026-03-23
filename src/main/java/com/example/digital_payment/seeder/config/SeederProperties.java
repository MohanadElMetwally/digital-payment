package com.example.digital_payment.seeder.config;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.superuser")
public record SeederProperties(String username, String email, String phone, String password,
    String firstName, String lastName, LocalDate dateOfBirth) {
}