package com.example.digital_payment.identity.api.facade;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.api.dto.request.CreateUserRequest;
import com.example.digital_payment.identity.api.dto.request.UpdatePasswordRequest;
import com.example.digital_payment.identity.api.dto.request.UpdateUserRequest;
import com.example.digital_payment.identity.api.dto.response.UserResponse;
import com.example.digital_payment.identity.api.mapper.UserApiMapper;
import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UpdatePasswordCommand;
import com.example.digital_payment.identity.application.dto.UpdateUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.port.in.GetCurrentUserUseCase;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.identity.application.port.in.UpdatePasswordUseCase;
import com.example.digital_payment.identity.application.port.in.UpdateUserUseCase;

@Component
public class UserFacade {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserApiMapper userApiMapper;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;

    public UserFacade(RegisterUserUseCase registerUserUseCase, GetUserUseCase getUserUseCase,
        GetCurrentUserUseCase getCurrentUserUseCase, UserApiMapper userApiMapper,
        UpdateUserUseCase updateUserUseCase, UpdatePasswordUseCase updatePasswordUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.userApiMapper = userApiMapper;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
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

    public UserResponse updateUserById(UUID id, UpdateUserRequest request) {
        UpdateUserCommand command = userApiMapper.toUpdateUserCommand(request);
        UserResult result = updateUserUseCase.updateById(id, command);
        return userApiMapper.toResponse(result);
    }

    public UserResponse updateUserMe(UUID id, UpdateUserRequest request) {
        UpdateUserCommand command = userApiMapper.toUpdateUserCommand(request);
        UserResult result = updateUserUseCase.updateMe(id, command);
        return userApiMapper.toResponse(result);
    }

    public void updatePassword(UUID id, UpdatePasswordRequest request) {
        UpdatePasswordCommand command = userApiMapper.toUpdatePasswordCommand(request);
        updatePasswordUseCase.updatePassword(id, command);
    }
}