package com.kyoofus.security.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.kyoofus.security.config.SecurityConfig;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** 로그인 성공시 처리하는 핸들러이다. */
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException, jakarta.servlet.ServletException {

        setDefaultTargetUrl( SecurityConfig.DEFAULT_SUCCESS_URL);  //"/home"
                // 로그인 후 핸들러가 직접 리다이렉트할지 여부를 설정합니다.
        setRedirectStrategy(new DefaultRedirectStrategy());
        super.onAuthenticationSuccess(request, response, authentication);

    }
}