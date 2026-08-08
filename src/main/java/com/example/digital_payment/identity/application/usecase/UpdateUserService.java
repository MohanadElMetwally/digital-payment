package com.example.digital_payment.identity.application.usecase;

import java.util.UUID;

import com.example.digital_payment.identity.application.dto.UpdateUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.UpdateUserUseCase;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.UpdateUserPort;
import com.example.digital_payment.identity.domain.exceptions.UserNotFoundException;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class UpdateUserService implements UpdateUserUseCase {
    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;
    private final UserMapper userMapper;
    private final TransactionPort transactionPort;

    public UpdateUserService(LoadUserPort loadUserPort, UpdateUserPort updateUserPort,
        TransactionPort transactionPort, UserMapper userMapper) {
        this.updateUserPort = updateUserPort;
        this.userMapper = userMapper;
        this.loadUserPort = loadUserPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public UserResult updateById(UUID id, UpdateUserCommand command) {
        // TODO: Add Admins can't update other admins
        // TODO: Add Superusers can't update other superusers
        return transactionPort.execute(() -> {
            Users user = loadUserPort.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            user.update(command.email(), command.phone());
            Users updated = updateUserPort.update(user);
            return userMapper.toResult(updated);
        });
    }

    @Override
    public UserResult updateMe(UUID id, UpdateUserCommand command) {
        return transactionPort.execute(() -> {
            Users user = loadUserPort.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            user.update(command.email(), command.phone());
            Users updated = updateUserPort.update(user);
            return userMapper.toResult(updated);
        });
    }
}
