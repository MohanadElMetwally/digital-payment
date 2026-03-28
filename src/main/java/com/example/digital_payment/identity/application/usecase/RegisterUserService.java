package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.identity.application.port.out.EventPublisherPort;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.application.port.out.ResolveCountryPort;
import com.example.digital_payment.identity.application.port.out.ResolveCurrencyPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.application.port.out.TransactionPort;
import com.example.digital_payment.identity.domain.exceptions.EmailAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.PhoneAlreadyExistsException;
import com.example.digital_payment.identity.domain.exceptions.UsernameAlreadyExistsException;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.domain.model.valueobjects.UserRegistrationData;
import com.example.digital_payment.shared.events.UserRegisteredEvent;

public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final UserMapper userMapper;
    private final ResolveCountryPort resolveCountryPort;
    private final ResolveCurrencyPort resolveCurrencyPort;
    private final EventPublisherPort eventPublisherPort;
    private final TransactionPort transactionPort;

    public RegisterUserService(LoadUserPort loadUserPort, SaveUserPort saveUserPort,
        ResolveCountryPort resolveCountryPort, ResolveCurrencyPort resolveCurrencyPort,
        EventPublisherPort eventPublisherPort, TransactionPort transactionPort,
        UserMapper userMapper) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.userMapper = userMapper;
        this.resolveCountryPort = resolveCountryPort;
        this.resolveCurrencyPort = resolveCurrencyPort;
        this.eventPublisherPort = eventPublisherPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public UserResult register(RegisterUserCommand command) {
        return transactionPort.execute(() -> {
            loadUserPort
                .findByEmailOrUsernameOrPhone(command.email(), command.username(), command.phone())
                .ifPresent(exists -> {
                    if (exists.getEmail().equals(command.email())) {
                        throw new EmailAlreadyExistsException(command.email());
                    }
                    if (exists.getUsername().equals(command.username())) {
                        throw new UsernameAlreadyExistsException(command.username());
                    }
                    if (exists.getPhone().equals(command.phone())) {
                        throw new PhoneAlreadyExistsException(command.phone());
                    }
                });

            String country = resolveCountryPort.resolveCountry(command.phone());
            String currency = resolveCurrencyPort.resolveCurrency(country);

            UserRegistrationData registrationData = new UserRegistrationData(command.username(),
                command.email(), command.phone(), command.password(), command.role(),
                command.firstName(), command.lastName(), country, command.dateOfBirth());
            Users user = Users.register(registrationData);

            Users save = saveUserPort.save(user);

            eventPublisherPort.publish(new UserRegisteredEvent(save.getId(), currency));

            return userMapper.toResult(save);
        });
    }

}
