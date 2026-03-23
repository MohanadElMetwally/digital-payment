package com.example.digital_payment.identity.infrastructure.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.digital_payment.identity.domain.model.entities.Users;
import com.example.digital_payment.identity.infrastructure.dto.CurrentUser;

public class UserPrincipal implements UserDetails {

    private final Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Users getUser() {
        return this.user;
    }

    public CurrentUser getCurrentUser() {
        return new CurrentUser(this.user.getId(), this.user.getUsername(), this.user.getEmail(),
            this.user.getPhone(), this.user.getRole(), this.user.getStatus(),
            this.user.getCreatedAt(), this.user.getUpdatedAt());
    }
}