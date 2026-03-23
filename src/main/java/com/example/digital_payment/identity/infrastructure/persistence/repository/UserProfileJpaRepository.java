package com.example.digital_payment.identity.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.identity.infrastructure.persistence.entity.UserProfileEntity;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, UUID> {

}
