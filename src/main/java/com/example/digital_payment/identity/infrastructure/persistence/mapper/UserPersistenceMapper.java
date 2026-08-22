package com.example.digital_payment.identity.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.identity.domain.enums.UserUpdateFields;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.domain.model.snapshots.UserProfileSnapshot;
import com.example.digital_payment.identity.domain.model.snapshots.UserSnapshot;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserProfileEntity;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(Users user) {
        UserEntity e = new UserEntity();
        e.setId(user.getId());
        e.setUsername(user.getUsername());
        e.setEmail(user.getEmail());
        e.setPhone(user.getPhone());
        e.setRole(user.getRole());
        e.setStatus(user.getStatus());
        e.setCreatedAt(user.getCreatedAt());
        e.setUpdatedAt(user.getUpdatedAt());

        if (user.getProfile() != null) {
            UserProfileEntity profileEntity = new UserProfileEntity();
            profileEntity.setFirstName(user.getProfile().getFirstName());
            profileEntity.setLastName(user.getProfile().getLastName());
            profileEntity.setCountry(user.getProfile().getCountry());
            profileEntity.setDateOfBirth(user.getProfile().getDateOfBirth());
            profileEntity.setCreatedAt(user.getProfile().getCreatedAt());
            profileEntity.setUser(e);
            e.setUserProfile(profileEntity);
        }

        return e;
    }

    public Users toDomain(UserEntity e) {
        UserProfileSnapshot profileSnapshot = null;
        if (e.getUserProfile() != null) {
            profileSnapshot = new UserProfileSnapshot(e.getId(), e.getUserProfile().getFirstName(),
                    e.getUserProfile().getLastName(), e.getUserProfile().getCountry(),
                    e.getUserProfile().getDateOfBirth(), e.getUserProfile().getCreatedAt(),
                    e.getUserProfile().getUpdatedAt());
        }

        UserSnapshot snapshot = new UserSnapshot(e.getId(), e.getUsername(), e.getEmail(),
                e.getPhone(), e.getPasswordHash(), e.getRole(), e.getStatus(), e.getCreatedAt(),
                e.getUpdatedAt(), profileSnapshot);

        return Users.reconstitute(snapshot);
    }

    public UserEntity updateEntity(Users user, UserEntity entity) {
        if (user.pollChanged(UserUpdateFields.EMAIL)) {
            entity.setEmail(user.getEmail());
        }
        if (user.pollChanged(UserUpdateFields.PHONE)) {
            entity.setPhone(user.getPhone());
        }
        entity.setUpdatedAt(user.getUpdatedAt());
        return entity;
    }
}
