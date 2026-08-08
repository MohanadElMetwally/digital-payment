package com.example.digital_payment.identity.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByEmailOrUsernameOrPhone(String email, String username,
        String phone);

    Optional<UserEntity> findFirstBy();
}
