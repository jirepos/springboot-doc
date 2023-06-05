package com.kyoofus.custom;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/** 커스텀 인증을 처리하기 위한 Provider */
// Component 어노테이션을 붙이면 Basic, Form 인증에서 Provider를 못 찾음
//@Component
public class CustomProvider implements AuthenticationProvider {

    public CustomProvider() {}
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomToken customToken = new CustomToken(username, password);
        customToken.setAuthenticated(true);
        return customToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomToken.class.isAssignableFrom(authentication);
    }
}
