package com.kyoofus.custom;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/** 이 클래스에서 스프링 메커니즘을 사용하지 않는 경우 직접 인증을 처리하도록 구헌한다. */
public class CustomAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            CustomToken customToken = new CustomToken(username, password);
            customToken.setAuthenticated(true);
            return customToken;
        }
}