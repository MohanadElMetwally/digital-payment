package com.example.digital_payment.identity.application.usecase;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.domain.exceptions.UserNotFoundException;
import com.example.digital_payment.identity.domain.model.Users;

public class GetUserService implements GetUserUseCase {
    private final LoadUserPort loadUserPort;
    private final UserMapper userMapper;

    public GetUserService(LoadUserPort loadUserPort, UserMapper userMapper) {
        this.loadUserPort = loadUserPort;
        this.userMapper = userMapper;
    }

    @Override
    public UserResult getById(UUID userId) {
        Optional<Users> existingUser = loadUserPort.findById(userId);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return userMapper.toResult(existingUser.get());
    }

}
