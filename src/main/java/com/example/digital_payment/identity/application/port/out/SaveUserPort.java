package com.example.digital_payment.identity.application.port.out;

import com.example.digital_payment.identity.domain.model.Users;

public interface SaveUserPort {
    Users save(Users user);
}
