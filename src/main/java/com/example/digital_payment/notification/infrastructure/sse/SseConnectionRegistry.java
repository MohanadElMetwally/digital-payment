package com.example.digital_payment.notification.infrastructure.sse;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseConnectionRegistry {

    private final ConcurrentHashMap<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter register(UUID userId) {
        SseEmitter emitter = new SseEmitter(0L);
        SseEmitter previousEmitter = emitters.put(userId, emitter);

        if (previousEmitter != null) {
            previousEmitter.complete();
        }

        emitter.onCompletion(() -> remove(userId));
        emitter.onTimeout(() -> remove(userId));
        emitter.onError(ex -> remove(userId));
        return emitter;
    }

    public SseEmitter get(UUID userId) {
        return emitters.get(userId);
    }

    public void remove(UUID userId) {
        emitters.remove(userId);
    }

    public Map<UUID, SseEmitter> getAllEmitters() {
        return Collections.unmodifiableMap(emitters);
    }
}