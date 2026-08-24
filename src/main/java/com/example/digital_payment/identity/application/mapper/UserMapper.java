package com.example.digital_payment.identity.application.mapper;

import java.util.List;
import com.example.digital_payment.identity.application.dto.ProfileResult;
import com.example.digital_payment.identity.application.dto.UserResult;
import com.example.digital_payment.identity.domain.model.entities.Users;

public class UserMapper {
    public UserResult toResult(Users user) {
        ProfileResult profileResult = null;

        if (user.getProfile() != null) {
            profileResult = new ProfileResult(user.getProfile().getFirstName(),
                    user.getProfile().getLastName(), user.getProfile().getCountry(),
                    user.getProfile().getDateOfBirth());
        }

        return new UserResult(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(),
                user.getRole(), user.getStatus(), user.getCreatedAt(), profileResult);
    }

    public List<UserResult> toResultList(List<Users> users) {
        return users.stream().map(this::toResult).toList();
    }
}
