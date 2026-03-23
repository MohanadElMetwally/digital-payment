package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.GetCurrentUserUseCase;
import com.example.digital_payment.identity.application.port.out.CurrentUserPort;
import com.example.digital_payment.identity.domain.model.entities.Users;

public class GetCurrentUserService implements GetCurrentUserUseCase {

    private final CurrentUserPort getCurrentUserPort;
    private final UserMapper userMapper;

    public GetCurrentUserService(CurrentUserPort getCurrentUserPort, UserMapper userMapper) {
        this.getCurrentUserPort = getCurrentUserPort;
        this.userMapper = userMapper;
    }

    @Override
    public UserResult getCurrentUser() {
        Users user = getCurrentUserPort.getCurrentUser();
        return userMapper.toResult(user);
    }

}
