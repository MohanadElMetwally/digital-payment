package com.example.digital_payment.identity.application.usecase;

import java.util.UUID;

import com.example.digital_payment.identity.application.dto.UpdatePasswordCommand;
import com.example.digital_payment.identity.application.port.in.UpdatePasswordUseCase;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.PasswordMatchPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.application.port.out.UpdatePasswordPort;
import com.example.digital_payment.identity.domain.exceptions.InvalidPasswordException;
import com.example.digital_payment.identity.domain.exceptions.UserNotFoundException;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class UpdatePasswordService implements UpdatePasswordUseCase {
    private final LoadUserPort loadUserPort;
    private final PasswordMatchPort passwordMatchPort;
    private final UpdatePasswordPort updatePasswordPort;
    private final TransactionPort transactionPort;

    public UpdatePasswordService(LoadUserPort loadUserPort, SaveUserPort saveUserPort,
        PasswordMatchPort passwordMatchPort, UpdatePasswordPort updatePasswordPort,
        TransactionPort transactionPort) {
        this.loadUserPort = loadUserPort;
        this.passwordMatchPort = passwordMatchPort;
        this.updatePasswordPort = updatePasswordPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void updatePassword(UUID id, UpdatePasswordCommand command) {
        transactionPort.executeVoid(() -> {
            Users user = loadUserPort.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            if (!passwordMatchPort.matches(command.currentPassword(), user.getPassword())
                || !command.newPassword().equals(command.confirmNewPassword()))
                throw new InvalidPasswordException();
            user.updatePassword(command.newPassword());
            updatePasswordPort.updatePassword(user);
        });
    }
}
