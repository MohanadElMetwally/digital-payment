package com.example.digital_payment.identity.api.controller;

import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.identity.api.dto.request.CreateUserRequest;
import com.example.digital_payment.identity.api.dto.response.UserResponse;
import com.example.digital_payment.identity.api.facade.UserFacade;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getCurrentUser());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER', 'ADMIN')")
    public ResponseEntity<UserResponse> readUserById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.readUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.registerUser(request));
    }

    @PostMapping("/admins/register")
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.registerAdmin(request));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateUserMe() {
        throw new NotImplementedException();
    }
}