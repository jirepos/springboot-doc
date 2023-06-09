package com.kyoofus.security.custom;

import org.springframework.security.authentication.AbstractAuthenticationToken;


/** 커스텀 Authentication */
public class CustomSecurityToken extends AbstractAuthenticationToken {
    private String principal;
    private String credentials;

    public CustomSecurityToken(String customToken) {
        super(null);
        this.principal = "test";
        this.credentials = "test";
    }
    public CustomSecurityToken(String email, String credentials) {
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
