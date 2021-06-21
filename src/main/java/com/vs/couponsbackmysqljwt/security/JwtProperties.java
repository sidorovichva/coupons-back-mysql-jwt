package com.vs.couponsbackmysqljwt.security;

public class JwtProperties {
    public static final String SECRET = "SomeSecretForJWTGeneration99238475iuhfieh787hvho37yf83hf8co37";
    public static final int EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
