package com.kyoofus.custom;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/** 커스텀 인증을 처리하기 위한 Provider */
@Component
public class CustomSecurityProvider implements AuthenticationProvider {

    public CustomSecurityProvider() {}
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomSecurityToken customSecurityToken = new CustomSecurityToken(username, password);
        customSecurityToken.setAuthenticated(true);
        return customSecurityToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomSecurityToken.class.isAssignableFrom(authentication);
    }
}
