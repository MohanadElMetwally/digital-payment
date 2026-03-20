package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.domain.model.Users;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;
import com.example.digital_payment.identity.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.example.digital_payment.identity.infrastructure.persistence.repository.UserJpaRepository;

import jakarta.transaction.Transactional;

@Component
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {
    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper persistenceMapper;
    private final PasswordEncoder encoder;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository,
        UserPersistenceMapper persistenceMapper, PasswordEncoder encoder) {
        this.jpaRepository = jpaRepository;
        this.persistenceMapper = persistenceMapper;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public Users save(Users user) {
        UserEntity entity = persistenceMapper.toEntity(user);
        entity.setPasswordHash(encoder.encode(user.getPassword()));
        UserEntity save = jpaRepository.save(entity);
        return persistenceMapper.toDomain(save);
    }

    @Override
    public Optional<Users> findById(UUID id) {
        return jpaRepository.findById(id).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Users> findByEmailOrUsernameOrPhone(String email, String username,
        String Phone) {
        return jpaRepository.findByEmailOrUsernameOrPhone(email, username, Phone)
            .map(persistenceMapper::toDomain);
    }

    @Override
    public Users findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(persistenceMapper::toDomain).orElse(null);
    }

}
