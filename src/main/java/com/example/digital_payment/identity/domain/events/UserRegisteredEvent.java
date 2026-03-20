package com.example.digital_payment.identity.domain.events;

import java.util.UUID;

public record UserRegisteredEvent(UUID userId, String email) implements DomainEvent {
}