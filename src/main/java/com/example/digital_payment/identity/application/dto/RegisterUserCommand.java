package com.example.digital_payment.identity.application.dto;

import java.time.LocalDate;
import com.example.digital_payment.identity.domain.enums.UserRole;

public record RegisterUserCommand(String username, String email, String phone, String password,
        UserRole role, String firstName, String lastName, LocalDate dateOfBirth) {
    public static RegisterUserCommand asUser(String username, String email, String phone,
            String password, String firstName, String lastName, LocalDate dateOfBirth) {
        return new RegisterUserCommand(username, email, phone, password, UserRole.USER, firstName,
                lastName, dateOfBirth);
    }

    public static RegisterUserCommand asAdmin(String username, String email, String phone,
            String password, String firstName, String lastName, LocalDate dateOfBirth) {
        return new RegisterUserCommand(username, email, phone, password, UserRole.ADMIN, firstName,
                lastName, dateOfBirth);
    }

    public static RegisterUserCommand asSuperuser(String username, String email, String phone,
            String password, String firstName, String lastName, LocalDate dateOfBirth) {
        return new RegisterUserCommand(username, email, phone, password, UserRole.SUPERUSER,
                firstName, lastName, dateOfBirth);
    }
}
