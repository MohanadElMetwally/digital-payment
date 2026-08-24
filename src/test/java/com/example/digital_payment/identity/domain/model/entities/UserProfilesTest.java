package com.example.digital_payment.identity.domain.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.example.digital_payment.identity.domain.model.snapshots.UserProfileSnapshot;
import com.example.digital_payment.identity.domain.model.valueobjects.UserProfileRegistrationData;

class UserProfilesTest {
    @Test
    void register_validData_createsUserProfile() {
        UUID userId = UUID.randomUUID();
        UserProfileRegistrationData data = new UserProfileRegistrationData(userId, "John", "Doe",
                "US", LocalDate.now().minusYears(25));
        UserProfiles profile = UserProfiles.register(data);
        assertEquals(userId, profile.getUserId());
        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertEquals("US", profile.getCountry());
        assertEquals(data.dateOfBirth(), profile.getDateOfBirth());
        assertNotNull(profile.getCreatedAt());
        assertNull(profile.getUpdatedAt());
    }

    @Test
    void register_under18_throwsException() {
        UUID userId = UUID.randomUUID();
        UserProfileRegistrationData data = new UserProfileRegistrationData(userId, "Jane", "Smith",
                "UK", LocalDate.now().minusYears(17));
        assertThrows(InvalidUserDataException.class, () -> UserProfiles.register(data));
    }

    @Test
    void register_over100_throwsException() {
        UUID userId = UUID.randomUUID();
        UserProfileRegistrationData data = new UserProfileRegistrationData(userId, "Old", "Person",
                "CA", LocalDate.now().minusYears(101));
        assertThrows(InvalidUserDataException.class, () -> UserProfiles.register(data));
    }

    @Test
    void register_nullDateOfBirth_throwsException() {
        UUID userId = UUID.randomUUID();
        UserProfileRegistrationData data =
                new UserProfileRegistrationData(userId, "No", "DOB", "FR", null);
        assertThrows(InvalidUserDataException.class, () -> UserProfiles.register(data));
    }

    @Test
    void reconstitute_validSnapshot_createsUserProfile() {
        UUID userId = UUID.randomUUID();
        LocalDate dob = LocalDate.now().minusYears(30);
        LocalDateTime created = LocalDateTime.now().minusDays(10);
        LocalDateTime updated = LocalDateTime.now();
        UserProfileSnapshot snapshot =
                new UserProfileSnapshot(userId, " Alice ", " Bob ", "DE", dob, created, updated);
        UserProfiles profile = UserProfiles.reconstitute(snapshot);
        assertEquals(userId, profile.getUserId());
        assertEquals("Alice", profile.getFirstName());
        assertEquals("Bob", profile.getLastName());
        assertEquals("DE", profile.getCountry());
        assertEquals(dob, profile.getDateOfBirth());
        assertEquals(created, profile.getCreatedAt());
        assertEquals(updated, profile.getUpdatedAt());
    }
}
