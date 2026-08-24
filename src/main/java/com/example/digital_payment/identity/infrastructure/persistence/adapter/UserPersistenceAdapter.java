package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.application.port.out.UpdatePasswordPort;
import com.example.digital_payment.identity.application.port.out.UpdateUserPort;
import com.example.digital_payment.identity.domain.exceptions.EmailAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.PhoneAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.UserPersistenceException;
import com.example.digital_payment.identity.domain.exceptions.UsernameAlreadyExistsException;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.infrastructure.persistence.entity.UserEntity;
import com.example.digital_payment.identity.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.example.digital_payment.identity.infrastructure.persistence.repository.UserJpaRepository;

@Component
public class UserPersistenceAdapter
        implements LoadUserPort, SaveUserPort, UpdateUserPort, UpdatePasswordPort {
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
        try {
            UserEntity entity = persistenceMapper.toEntity(user);
            entity.setPasswordHash(encoder.encode(user.getPassword()));
            UserEntity saved = userJpaRepository.saveAndFlush(entity);
            return persistenceMapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw resolveConflict(user, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    public Optional<Users> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(persistenceMapper::toDomain);
    }

    @Override
    public boolean existsAny() {
        return userJpaRepository.findFirstBy().isPresent();
    }

    @Override
    @Transactional
    public Users update(Users user) {
        UserEntity entity = userJpaRepository.getReferenceById(user.getId());
        persistenceMapper.updateEntity(user, entity);
        return persistenceMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void updatePassword(Users user) {
        UserEntity entity = userJpaRepository.getReferenceById(user.getId());
        entity.setPasswordHash(encoder.encode(user.getPassword()));
    }

    private RuntimeException resolveConflict(Users user, DataIntegrityViolationException ex) {
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof ConstraintViolationException cve) {
            String constraint = cve.getConstraintName();
            if ("uq_users_username".equals(constraint)) {
                return new UsernameAlreadyExistsException(user.getUsername());
            }
            if ("uq_users_email".equals(constraint)) {
                return new EmailAlreadyExistsException(user.getEmail());
            }
            if ("uq_users_phone".equals(constraint)) {
                return new PhoneAlreadyExistsException(user.getPhone());
            }
        }
        return new UserPersistenceException(ex.getMessage());
    }
}
