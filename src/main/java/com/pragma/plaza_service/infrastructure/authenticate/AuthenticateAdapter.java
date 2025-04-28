package com.pragma.plaza_service.infrastructure.authenticate;

import com.pragma.plaza_service.domain.spi.IAutthenticatePort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticateAdapter implements IAutthenticatePort {

    @Override
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return Long.parseLong((String) auth.getPrincipal());
        }
        return null;
    }

}
