package com.example.digital_payment.identity.domain.model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.example.digital_payment.identity.domain.model.snapshots.UserProfileSnapshot;
import com.example.digital_payment.identity.domain.model.valueobjects.UserProfileRegistrationData;

public class UserProfiles {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String country;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfiles() {}

    public static UserProfiles register(UserProfileRegistrationData registrationData) {
        validateDateOfBirth(registrationData.dateOfBirth());

        UserProfiles userProfile = new UserProfiles();
        userProfile.userId = registrationData.userId();
        userProfile.firstName = registrationData.firstName().trim();
        userProfile.lastName = registrationData.lastName().trim();
        userProfile.country = registrationData.country();
        userProfile.dateOfBirth = registrationData.dateOfBirth();
        userProfile.createdAt = LocalDateTime.now();
        userProfile.updatedAt = null;
        return userProfile;
    }

    public static UserProfiles reconstitute(UserProfileSnapshot snapshot) {
        UserProfiles userProfile = new UserProfiles();
        userProfile.userId = snapshot.userId();
        userProfile.firstName = snapshot.firstName().trim();
        userProfile.lastName = snapshot.lastName().trim();
        userProfile.country = snapshot.country();
        userProfile.dateOfBirth = snapshot.dateOfBirth();
        userProfile.createdAt = snapshot.createdAt();
        userProfile.updatedAt = snapshot.updatedAt();
        return userProfile;
    }

    private static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new InvalidUserDataException("Date of birth cannot be null");
        }

        LocalDate now = LocalDate.now();
        LocalDate eighteenYearsAgo = now.minusYears(18);
        LocalDate hundredYearsAgo = now.minusYears(100);

        if (dateOfBirth.isAfter(eighteenYearsAgo)) {
            throw new InvalidUserDataException("User must be at least 18 years old");
        }

        if (dateOfBirth.isBefore(hundredYearsAgo)) {
            throw new InvalidUserDataException("User age cannot exceed 100 years");
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
