package com.pragma.plaza_service.infrastructure.security;

import com.pragma.plaza_service.domain.model.RoleEnum;
import com.pragma.plaza_service.infrastructure.security.jwt.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/restaurant").hasRole(RoleEnum.ADMIN.name())
                                        .requestMatchers("/dish").hasRole(RoleEnum.OWNER.name())
                                        .requestMatchers("/dish/{id}").hasRole(RoleEnum.OWNER.name())
                                        .requestMatchers("/dish/status/{id}").hasRole(RoleEnum.OWNER.name())
                                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
