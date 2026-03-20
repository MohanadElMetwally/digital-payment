package com.example.digital_payment.identity.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.identity.api.dto.request.CreateUserRequest;
import com.example.digital_payment.identity.api.dto.response.UserResponse;
import com.example.digital_payment.identity.application.dto.RegisterUserCommand;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.application.port.in.GetUserUseCase;
import com.example.digital_payment.identity.application.port.in.RegisterUserUseCase;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, GetUserUseCase getUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> readUser(@PathVariable UUID id) {
        UserResult result = getUserUseCase.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(result));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody CreateUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(request.username(), request.email(),
            request.phone(), request.password());
        UserResult result = registerUserUseCase.register(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(result));
    }

}
