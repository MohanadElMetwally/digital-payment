package com.example.digital_payment.notification.infrastructure.sse;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.digital_payment.notification.application.port.out.NotificationPublisherPort;
import com.example.digital_payment.shared.dto.NotificationMessage;

@Component
public class SseNotificationPublisher implements NotificationPublisherPort {

    private final SseConnectionRegistry registry;
    private final ExecutorService notificationExecutor;

    public SseNotificationPublisher(SseConnectionRegistry registry,
        ExecutorService notificationExecutor) {
        this.registry = registry;
        this.notificationExecutor = notificationExecutor;
    }

    @Override
    public void publish(UUID userId, NotificationMessage<?> message) {
        notificationExecutor.execute(() -> {
            SseEmitter emitter = registry.get(userId);
            if (emitter == null)
                return;
            send(userId, emitter, message);
        });
    }

    private void send(UUID userId, SseEmitter emitter, NotificationMessage<?> message) {
        try {
            emitter.send(SseEmitter.event().name(message.type().toString()).data(message));
        } catch (Exception ex) {
            registry.remove(userId);
            emitter.completeWithError(ex);
        }
    }
}