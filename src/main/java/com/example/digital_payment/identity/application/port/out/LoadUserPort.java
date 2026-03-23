package com.example.digital_payment.identity.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.identity.domain.model.entities.Users;

public interface LoadUserPort {
    Users findByUsername(String username);

    Optional<Users> findById(UUID id);

    Optional<Users> findByEmailOrUsernameOrPhone(String email, String username, String Phone);

    boolean existsAny();

}
