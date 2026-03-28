package com.example.digital_payment.identity.application.port.out;

import com.example.digital_payment.identity.domain.model.entities.Users;

public interface UpdatePasswordPort {
    void updatePassword(Users user);
}
