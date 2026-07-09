package com.example.digital_payment.payment.infrastructure.idempotency;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.port.out.IdempotencyStore;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

@Component
public class RedisIdempotencyStore implements IdempotencyStore {
    private final RedisTemplate<String, Transactions> redisTemplate;

    @Value("${idempotency.ttl.hours}")
    private long IDEMPOTENCY_TTL_HOURS;

    public RedisIdempotencyStore(RedisTemplate<String, Transactions> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<Transactions> get(UUID userId, UUID key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(buildKey(userId, key)));
    }

    @Override
    public void save(UUID userId, UUID key, Transactions transaction) {
        redisTemplate.opsForValue()
            .set(buildKey(userId, key), transaction, Duration.ofHours(IDEMPOTENCY_TTL_HOURS));
    }

    private String buildKey(UUID userId, UUID idempotencyKey) {
        return "payments:%s:%s".formatted(userId, idempotencyKey);
    }
}
