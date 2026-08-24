package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.digital_payment.identity.application.port.out.CurrentUserPort;
import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.infrastructure.security.UserPrincipal;

@Component
public class SecurityCurrentUserAdapter implements CurrentUserPort {
    @Override
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUser();
    }
}
