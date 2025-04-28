package com.pragma.plaza_service.infrastructure.security.jwt;

import com.pragma.plaza_service.domain.model.RoleEnum;
import com.pragma.plaza_service.infrastructure.security.constants.JWTConstants;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(JWTConstants.AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(JWTConstants.BEARER_PREFIX)) {
            String token = authorizationHeader.substring(JWTConstants.BEARER_PREFIX.length());
            Claims claims = jwtService.getClaimsFromToken(token);

            String username = claims.getSubject();
            String roleStr = claims.get(JWTConstants.ROLE, String.class);
            RoleEnum role = RoleEnum.valueOf(roleStr);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(new SimpleGrantedAuthority(JWTConstants.ROLE_SECURITY_PREFIX +  role.name())));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }
}
