package com.ist.signature.utils;

public class Constants {
    private Constants() {}

    public static final String SIGNATURE = "signature";
    public static final String API_V1 = "/api/v1";
    public static final String AUTH_PATH = API_V1 + "/auth";
    public static final String COMPANY_PATH = API_V1 + "/company";
    public static final String USERS_PATH= API_V1 + "/users";

    public static final String JWT_TOKEN_PREFIX = "Bearer";
    public static final String JWT_HEADER = "Authorization";

    public static final int TOKEN_EXPIRATION = 3600;
}
