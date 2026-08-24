package com.example.digital_payment.shared.events;

import java.util.UUID;
import com.example.digital_payment.shared.dto.NotificationMessage;

public record SseNotificationEvent<T>(UUID userId, NotificationMessage<T> message) {
}
