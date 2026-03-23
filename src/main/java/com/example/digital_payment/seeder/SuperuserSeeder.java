package com.example.digital_payment.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.port.in.CheckUsersExistUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;
import com.example.digital_payment.seeder.config.SeederProperties;

@Component
@EnableConfigurationProperties(SeederProperties.class)
public class SuperuserSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SuperuserSeeder.class);

    private final RegisterUserUseCase registerUserUseCase;
    private final CheckUsersExistUseCase checkUsersExistUseCase;
    private final SeederProperties props;

    public SuperuserSeeder(RegisterUserUseCase registerUserUseCase,
        CheckUsersExistUseCase checkUsersExistUseCase, SeederProperties props) {
        this.registerUserUseCase = registerUserUseCase;
        this.checkUsersExistUseCase = checkUsersExistUseCase;
        this.props = props;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (checkUsersExistUseCase.hasAnyUsers()) {
            log.info("[Seeder] Users already exist. Skipping.");
            return;
        }

        registerUserUseCase.register(
            RegisterUserCommand.asSuperuser(props.username(), props.email(), props.phone(),
                props.password(), props.firstName(), props.lastName(), props.dateOfBirth()));

        log.info("[Seeder] Superuser created: {} {}", props.firstName(), props.lastName());
    }
}