package com.kyoofus.custom;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomToken extends AbstractAuthenticationToken {
    private String principal;
    private String credentials;

    public CustomToken(String customToken) {
        super(null);
        this.principal = "test";
        this.credentials = "test";
    }
    public CustomToken(String email, String credentials) {
        super(null);
        this.principal = email;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
