package com.example.digital_payment.shared.config.redis;

import java.time.Duration;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisSerializer<Object> redisValueSerializer(ObjectMapper objectMapper) {
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType("com.example.digital_payment")
            .allowIfSubType("java.math")
            .allowIfSubType("java.time")
            .allowIfSubType("java.util")
            .build();

        ObjectMapper redisMapper = objectMapper.rebuild()
            .activateDefaultTyping(typeValidator, DefaultTyping.NON_FINAL)
            .build();

        return new GenericJacksonJsonRedisSerializer(redisMapper);
    }

    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(
        RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisKeySerializer))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(
        RedisCacheConfiguration defaultConfig) {
        return builder -> builder.withCacheConfiguration("wallets",
            defaultConfig.entryTtl(Duration.ofHours(1)));
    }
}