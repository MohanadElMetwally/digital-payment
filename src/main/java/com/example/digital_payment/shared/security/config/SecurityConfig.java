package com.example.digital_payment.shared.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.digital_payment.shared.security.filters.JWTFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;
    private final PasswordEncoder encoder;

    public SecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter,
        PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
        this.encoder = encoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(req -> req
            .requestMatchers("/api/v1/users/register", "/api/v1/auth/login", "/swagger-ui.html",
                "/swagger-ui/**", "/v3/api-docs/**", "/docs", "/docs/**",
                "/swagger-resources/**", "/webjars/**")
            .permitAll()
            .anyRequest()
            .authenticated())
            .formLogin(form -> form.disable())
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
            res.sendError(401, "Could not validate credentials");
            }))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
        throws Exception {
        return config.getAuthenticationManager();
    }
}
