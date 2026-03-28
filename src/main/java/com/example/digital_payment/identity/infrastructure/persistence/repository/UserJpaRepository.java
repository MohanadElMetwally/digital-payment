package com.example.digital_payment.identity.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByEmailOrUsernameOrPhone(String email, String username,
        String phone);

    Optional<UserEntity> findFirstBy();

    @Modifying
    @Query("UPDATE UserEntity u SET u.email = :email WHERE u.id = :id")
    void updateEmail(@Param("id") UUID id, @Param("email") String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.phone = :phone WHERE u.id = :id")
    void updatePhone(@Param("id") UUID id, @Param("phone") String phone);

    @Modifying
    @Query("UPDATE UserEntity u SET u.updatedAt = :updatedAt WHERE u.id = :id")
    void updateUpdatedAt(@Param("id") UUID id, @Param("updatedAt") LocalDateTime updatedAt);
}
