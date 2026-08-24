package com.example.digital_payment.payment.infrastructure.idempotency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import tools.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
public class RedisIdempotencyConfig {
    @Bean
    public RedisTemplate<String, Transactions> transactionRedisTemplate(
            RedisConnectionFactory connectionFactory, RedisSerializer<String> redisKeySerializer,
            RedisSerializer<Object> redisValueSerializer, ObjectMapper objectMapper) {

        RedisTemplate<String, Transactions> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(redisKeySerializer);
        template.setValueSerializer(
                new JacksonJsonRedisSerializer<>(objectMapper, Transactions.class));

        template.afterPropertiesSet();
        return template;
    }
}
