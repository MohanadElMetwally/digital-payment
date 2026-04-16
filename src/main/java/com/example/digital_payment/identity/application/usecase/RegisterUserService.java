package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.mapper.UserMapper;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.identity.application.port.out.EventPublisherPort;
import com.example.digital_payment.identity.application.port.out.PhoneValidatorPort;
import com.example.digital_payment.identity.application.port.out.ResolveCountryPort;
import com.example.digital_payment.identity.application.port.out.ResolveCurrencyPort;
import com.example.digital_payment.identity.application.port.out.SaveUserPort;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.domain.model.valueobjects.UserRegistrationData;
import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.shared.events.UserRegisteredEvent;

public class RegisterUserService implements RegisterUserUseCase {

    private final SaveUserPort saveUserPort;
    private final UserMapper userMapper;
    private final ResolveCountryPort resolveCountryPort;
    private final ResolveCurrencyPort resolveCurrencyPort;
    private final EventPublisherPort eventPublisherPort;
    private final TransactionPort transactionPort;
    private final PhoneValidatorPort phoneValidatorPort;

    public RegisterUserService(SaveUserPort saveUserPort,
        ResolveCountryPort resolveCountryPort, ResolveCurrencyPort resolveCurrencyPort,
        EventPublisherPort eventPublisherPort, TransactionPort transactionPort,
        PhoneValidatorPort phoneValidatorPort, UserMapper userMapper) {
        this.saveUserPort = saveUserPort;
        this.userMapper = userMapper;
        this.resolveCountryPort = resolveCountryPort;
        this.resolveCurrencyPort = resolveCurrencyPort;
        this.eventPublisherPort = eventPublisherPort;
        this.transactionPort = transactionPort;
        this.phoneValidatorPort = phoneValidatorPort;
    }

    @Override
    public UserResult register(RegisterUserCommand command) {
        phoneValidatorPort.validate(command.phone());

        return transactionPort.execute(() -> {
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
