package com.example.digital_payment.identity.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;
import com.example.digital_payment.identity.domain.events.DomainEvent;
import com.example.digital_payment.identity.domain.events.UserRegisteredEvent;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;

public class Users {
    private UUID id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Users() {
    }

    public static Users register(String username, String email, String phone, String password) {
        validateUsername(username);
        validateEmail(email);
        validatePhone(phone);

        Users user = new Users();
        user.id = UUID.randomUUID();
        user.username = username.toLowerCase().trim();
        user.email = email.toLowerCase().trim();
        user.phone = phone.trim();
        user.password = password;
        user.role = UserRole.USER;
        user.status = UserStatus.ACTIVE;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = null;

        user.domainEvents.add(new UserRegisteredEvent(user.id, user.email));

        return user;
    }

    public static Users reconstitute(UUID id, String username, String email, String phone,
        String password, UserRole role, UserStatus status, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        Users user = new Users();
        user.id = id;
        user.username = username;
        user.email = email;
        user.phone = phone;
        user.password = password;
        user.role = role;
        user.status = status;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank())
            throw new InvalidUserDataException("Username cannot be blank");
        if (username.length() < 3 || username.length() > 30)
            throw new InvalidUserDataException("Username must be 3-30 characters");
    }

    private static void validateEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new InvalidUserDataException("Invalid email address");
    }

    private static void validatePhone(String phone) {
        if (phone == null || phone.isBlank())
            throw new InvalidUserDataException("Phone cannot be blank");
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
