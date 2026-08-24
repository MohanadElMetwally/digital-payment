package com.example.digital_payment.shared.config.redis;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${cache.expiration.ms}")
    private int CACHE_TTL;
    @Value("${wallet.expiration.ms}")
    private int WALLET_CACHE_TTL;
    @Value("${user-details.expiration.ms}")
    private int USER_DETAILS_CACHE_TTL;

    @Bean
    public RedisSerializer<Object> redisValueSerializer(ObjectMapper objectMapper) {
        // @formatter:off
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.example.digital_payment")
                .allowIfSubType("java.math")
                .allowIfSubType("java.time")
                .allowIfSubType("java.util")
                .build();
        // @formatter:on
        ObjectMapper redisMapper = objectMapper.rebuild()
                .activateDefaultTyping(typeValidator, DefaultTyping.NON_FINAL).build();

        return new GenericJacksonJsonRedisSerializer(redisMapper);
    }

    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(
            RedisSerializer<String> redisKeySerializer,
            RedisSerializer<Object> redisValueSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMillis(CACHE_TTL))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisKeySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisValueSerializer));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(
            RedisCacheConfiguration defaultConfig) {
        return builder -> builder
                .withCacheConfiguration("wallets",
                        defaultConfig.entryTtl(Duration.ofMillis(WALLET_CACHE_TTL)))
                .withCacheConfiguration("user-details",
                        defaultConfig.entryTtl(Duration.ofMillis(USER_DETAILS_CACHE_TTL)));
    }
}
