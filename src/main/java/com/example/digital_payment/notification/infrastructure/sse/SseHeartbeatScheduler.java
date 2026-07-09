package com.example.digital_payment.notification.infrastructure.sse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseHeartbeatScheduler {

    private final SseConnectionRegistry registry;

    public SseHeartbeatScheduler(SseConnectionRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedDelay = 15000)
    public void sendHeartbeats() {
        registry.getAllEmitters().forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().comment("ping"));
            } catch (Exception e) {
                registry.remove(userId);
                emitter.completeWithError(e);
            }
        });
    }
}
