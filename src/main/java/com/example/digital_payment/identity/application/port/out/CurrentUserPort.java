package com.example.digital_payment.identity.application.port.out;

import com.example.digital_payment.identity.domain.model.entities.Users;

public interface CurrentUserPort {
    Users getCurrentUser();
}
