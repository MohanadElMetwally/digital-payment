package com.example.digital_payment.identity.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.api.dto.request.CreateUserRequest;
import com.example.digital_payment.identity.api.dto.request.UpdatePasswordRequest;
import com.example.digital_payment.identity.api.dto.request.UpdateUserRequest;
import com.example.digital_payment.identity.api.dto.response.UserResponse;
import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UpdatePasswordCommand;
import com.example.digital_payment.identity.application.dto.UpdateUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;

@Component
public class UserApiMapper {

    public RegisterUserCommand toRegisterUserCommand(CreateUserRequest request) {
        return RegisterUserCommand.asUser(request.username(), request.email(), request.phone(),
            request.password(), request.firstName(), request.lastName(), request.dateOfBirth());
    }

    public RegisterUserCommand toRegisterAdminCommand(CreateUserRequest request) {
        return RegisterUserCommand.asAdmin(request.username(), request.email(), request.phone(),
            request.password(), request.firstName(), request.lastName(), request.dateOfBirth());
    }

    public UpdateUserCommand toUpdateUserCommand(UpdateUserRequest request) {
        return new UpdateUserCommand(request.email(), request.phone());
    }

    public UpdatePasswordCommand toUpdatePasswordCommand(UpdatePasswordRequest request) {
        return new UpdatePasswordCommand(request.currentPassword(), request.newPassword(),
            request.confirmNewPassword());
    }

    public UserResponse toResponse(UserResult result) {
        return UserResponse.from(result);
    }
}