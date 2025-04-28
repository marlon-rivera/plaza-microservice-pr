package com.pragma.plaza_service.infrastructure.security.constants;

public class JWTConstants {

    private JWTConstants(){}

    public static final String SECRET_KEY = "ASKJDNIUWIUEDNIJKASCSODIDKKDIJIDNJSKNISKCNVFICMIOFVRFCIVFRDCIVNRFDKFJGRVI";
    public static final String ROLE = "ROLE";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final long EXPIRATION_TIME = 86400000L;
    public static final String USER_NOT_FOUND_EMAIL = "User not found with this email: ";
    public static final String ROLE_SECURITY_PREFIX = "ROLE_";
}
