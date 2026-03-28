package com.example.digital_payment.identity.domain.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.digital_payment.identity.domain.enums.UserRole;
import com.example.digital_payment.identity.domain.enums.UserStatus;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.example.digital_payment.identity.domain.model.snapshots.UserSnapshot;
import com.example.digital_payment.identity.domain.model.valueobjects.UserRegistrationData;

public class UsersTest {

    private static final LocalDate VALID_DOB = LocalDate.of(2000, 1, 1);

    @Test
    void testRegister() {
        UserRegistrationData data = new UserRegistrationData("JohnDoe", "john@example.com",
            "1234567890", "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        Users user = Users.register(data);

        assertNotNull(user.getId());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertNotNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void testRegisterWithBlankUsername() {
        UserRegistrationData data = new UserRegistrationData("", "john@example.com", "1234567890",
            "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }

    @Test
    void testRegisterWithShortUsername() {
        UserRegistrationData data = new UserRegistrationData("ab", "john@example.com", "1234567890",
            "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }

    @Test
    void testRegisterWithLongUsername() {
        UserRegistrationData data = new UserRegistrationData("a".repeat(31), "john@example.com",
            "1234567890", "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }

    @Test
    void testRegisterWithInvalidEmail() {
        UserRegistrationData data = new UserRegistrationData("JohnDoe", "johnexample.com",
            "1234567890", "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }

    @Test
    void testRegisterWithBlankPhone() {
        UserRegistrationData data = new UserRegistrationData("JohnDoe", "john@example.com", "",
            "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }

    @Test
    void testReconstitute() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        UserSnapshot snapshot = new UserSnapshot(userId, "johndoe", "john@example.com",
            "1234567890", "hashedPassword", UserRole.USER, UserStatus.ACTIVE, now, now, null);

        Users user = Users.reconstitute(snapshot);

        assertEquals(userId, user.getId());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("hashedPassword", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testRegisterTrimsAndLowercasesUsername() {
        UserRegistrationData data = new UserRegistrationData("  JohnDoe  ", "john@example.com",
            "1234567890", "password123", UserRole.USER, "John", "Doe", "USA", VALID_DOB);

        Users user = Users.register(data);

        assertEquals("johndoe", user.getUsername());
    }

    @Test
    void testRegisterWithNullDateOfBirth() {
        UserRegistrationData data = new UserRegistrationData("JohnDoe", "john@example.com",
            "1234567890", "password123", UserRole.USER, "John", "Doe", "USA", null);

        assertThrows(InvalidUserDataException.class, () -> Users.register(data));
    }
}