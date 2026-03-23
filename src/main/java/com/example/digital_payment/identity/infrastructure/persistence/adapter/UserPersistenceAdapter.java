package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;
import com.example.digital_payment.identity.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.example.digital_payment.identity.infrastructure.persistence.repository.UserJpaRepository;

import jakarta.transaction.Transactional;

@Component
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {
    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper persistenceMapper;
    private final PasswordEncoder encoder;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository,
        UserPersistenceMapper persistenceMapper, PasswordEncoder encoder) {
        this.userJpaRepository = userJpaRepository;
        this.persistenceMapper = persistenceMapper;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public Users save(Users user) {
        UserEntity entity = persistenceMapper.toEntity(user);
        entity.setPasswordHash(encoder.encode(user.getPassword()));
        UserEntity save = userJpaRepository.save(entity);
        return persistenceMapper.toDomain(save);
    }

    @Override
    public Optional<Users> findById(UUID id) {
        return userJpaRepository.findById(id).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Users> findByEmailOrUsernameOrPhone(String email, String username,
        String Phone) {
        return userJpaRepository.findByEmailOrUsernameOrPhone(email, username, Phone)
            .map(persistenceMapper::toDomain);
    }

    @Override
    public Users findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
            .map(persistenceMapper::toDomain)
            .orElse(null);
    }

    @Override
    public boolean existsAny() {
        return userJpaRepository.findFirstBy().isPresent();
    }
}
