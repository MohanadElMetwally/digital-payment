package com.example.digital_payment.shared.events;

import java.util.UUID;

public record UserRegisteredEvent(UUID userId, String currency, String email, String firstName,
        String lastName) implements DomainEvent {
}
