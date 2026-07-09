package com.example.digital_payment.notification.infrastructure.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.notification.application.port.in.SendSseNotificationUseCase;
import com.example.digital_payment.shared.events.SseNotificationEvent;

@Component
public class SseNotificationEventListener {
    private final SendSseNotificationUseCase sendSseNotificationUseCase;

    public SseNotificationEventListener(SendSseNotificationUseCase sendSseNotificationUseCase) {
        this.sendSseNotificationUseCase = sendSseNotificationUseCase;
    }

    @EventListener
    public void on(SseNotificationEvent<?> event) {
        sendSseNotificationUseCase.handle(event.userId(), event.message());
    }
}
