package com.hallmann.Poorira.security;

public class SecurityConstants {
    public static final String SECRET = "$un4!$4nu$B4cKw4Rd$";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/register";
}