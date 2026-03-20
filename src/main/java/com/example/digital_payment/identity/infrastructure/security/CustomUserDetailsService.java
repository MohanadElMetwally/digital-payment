package com.example.digital_payment.identity.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.digital_payment.identity.application.port.out.LoadUserPort;
import com.example.digital_payment.identity.domain.model.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final LoadUserPort loadUserPort;

    public CustomUserDetailsService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = loadUserPort.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                "User with username: %s was not found".formatted(username));
        }
        return new UserPrincipal(user);
    }
}
