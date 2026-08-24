package com.example.digital_payment.identity.api.dto.response;

import com.example.digital_payment.identity.application.dto.ProfileResult;

public record ProfileResponse(String firstName, String lastName, String country,
        String dateOfBirth) {
    public static ProfileResponse from(ProfileResult result) {
        return new ProfileResponse(result.firstName(), result.lastName(), result.country(),
                result.dateOfBirth().toString());
    }
}
