package com.example.digital_payment.notification.infrastructure.sse;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.domain.enums.SseNotificationType;
import com.example.digital_payment.shared.dto.MessageResponse;
import com.example.digital_payment.shared.dto.NotificationMessage;
import com.example.digital_payment.shared.events.SseNotificationEvent;

@Component
public class SseConnection {

    private final SseConnectionRegistry registry;
    private final EventPublisherPort publisher;

    public SseConnection(SseConnectionRegistry registry, EventPublisherPort publisher) {
        this.registry = registry;
        this.publisher = publisher;
    }

    public SseEmitter connect(UUID userId) {
        SseEmitter emitter = registry.register(userId);
        publisher.publish(new SseNotificationEvent<MessageResponse>(userId,
            new NotificationMessage<MessageResponse>(SseNotificationType.SYSTEM,
                new MessageResponse("Connected successfully!"))));
        return emitter;
    }
}
