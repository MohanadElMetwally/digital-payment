package com.example.digital_payment.identity.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.domain.model.Users;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;

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
        return e;
    }

    public Users toDomain(UserEntity e) {
        return Users.reconstitute(e.getId(), e.getUsername(), e.getEmail(), e.getPhone(),
            e.getPasswordHash(), e.getRole(), e.getStatus(), e.getCreatedAt(), e.getUpdatedAt());
    }
}