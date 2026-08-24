package com.example.digital_payment.identity.domain.model.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;
import com.example.digital_payment.identity.domain.enums.UserUpdateFields;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.example.digital_payment.identity.domain.model.snapshots.UserSnapshot;
import com.example.digital_payment.identity.domain.model.valueobjects.UserProfileRegistrationData;
import com.example.digital_payment.identity.domain.model.valueobjects.UserRegistrationData;

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

    private UserProfiles profile;

    private final Set<UserUpdateFields> changedFields = new HashSet<>();

    public Users() {}

    public static Users register(UserRegistrationData registrationData) {
        validateUsername(registrationData.username());
        validateEmail(registrationData.email());
        validatePhone(registrationData.phone());

        Users user = new Users();
        user.id = UUID.randomUUID();
        user.username = registrationData.username().toLowerCase().trim();
        user.email = registrationData.email().toLowerCase().trim();
        user.phone = registrationData.phone().trim();
        user.password = registrationData.password();
        user.role = registrationData.role();
        user.status = UserStatus.ACTIVE;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = null;

        UserProfileRegistrationData profileData = new UserProfileRegistrationData(user.id,
                registrationData.firstName(), registrationData.lastName(),
                registrationData.country(), registrationData.dateOfBirth());
        user.profile = UserProfiles.register(profileData);

        return user;
    }

    public static Users reconstitute(UserSnapshot snapshot) {
        Users user = new Users();
        user.id = snapshot.id();
        user.username = snapshot.username();
        user.email = snapshot.email();
        user.phone = snapshot.phone();
        user.password = snapshot.password();
        user.role = snapshot.role();
        user.status = snapshot.status();
        user.createdAt = snapshot.createdAt();
        user.updatedAt = snapshot.updatedAt();

        if (snapshot.profile() != null) {
            user.profile = UserProfiles.reconstitute(snapshot.profile());
        }

        return user;
    }

    public Users update(String email, String phone) {
        if (email != null) {
            validateEmail(email);
            this.email = email.toLowerCase().trim();
            changedFields.add(UserUpdateFields.EMAIL);
        }
        if (phone != null) {
            validatePhone(phone);
            this.phone = phone.trim();
            changedFields.add(UserUpdateFields.PHONE);
        }
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public boolean pollChanged(UserUpdateFields field) {
        return changedFields.remove(field);
    }

    public Users updatePassword(String password) {
        this.password = password;
        return this;
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

    public UserProfiles getProfile() {
        return profile;
    }

}
