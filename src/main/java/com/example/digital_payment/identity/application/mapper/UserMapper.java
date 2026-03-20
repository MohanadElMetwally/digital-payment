package com.example.digital_payment.identity.application.mapper;

import java.util.List;

import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.domain.model.Users;

public class UserMapper {
    public UserResult toResult(Users user) {
        return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(),
            user.getRole(), user.getStatus(), user.getCreatedAt());
    }

    public List<UserResult> toResultList(List<Users> users) {
        return users.stream().map(this::toResult).toList();
    }
}
