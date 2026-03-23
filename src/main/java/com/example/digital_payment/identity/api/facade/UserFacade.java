package com.example.digital_payment.identity.api.facade;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.api.dto.request.CreateUserRequest;
import com.example.digital_payment.identity.api.dto.response.UserResponse;
import com.example.digital_payment.identity.api.mapper.UserApiMapper;
import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.port.in.GetCurrentUserUseCase;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;

@Component
public class UserFacade {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserApiMapper userApiMapper;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public UserFacade(RegisterUserUseCase registerUserUseCase, GetUserUseCase getUserUseCase,
        GetCurrentUserUseCase getCurrentUserUseCase, UserApiMapper userApiMapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.userApiMapper = userApiMapper;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public UserResponse getCurrentUser() {
        UserResult result = getCurrentUserUseCase.getCurrentUser();
        return userApiMapper.toResponse(result);
    }

    public UserResponse readUserById(UUID id) {
        UserResult result = getUserUseCase.getById(id);
        return userApiMapper.toResponse(result);
    }

    public UserResponse registerUser(CreateUserRequest request) {
        RegisterUserCommand command = userApiMapper.toRegisterUserCommand(request);
        UserResult result = registerUserUseCase.register(command);
        return userApiMapper.toResponse(result);
    }

    public UserResponse registerAdmin(CreateUserRequest request) {
        RegisterUserCommand command = userApiMapper.toRegisterAdminCommand(request);
        UserResult result = registerUserUseCase.register(command);
        return userApiMapper.toResponse(result);
    }
}