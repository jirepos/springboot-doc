package com.kyoofus.security.handlers;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 로그인 실패시 처리하는 핸들러이다.
 */
@Component
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // exception.getMessage() shows the error message 
        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            // 로그인 실패
        } else if(exception instanceof LockedException) {
            // 계정 잠김
        } else if(exception instanceof DisabledException) {
            // 계정 비활성화
        } else if(exception instanceof AccountExpiredException) {
            // 계정 만료
        } else if(exception instanceof CredentialsExpiredException) {
            // 비밀번호 만료
        }
        // 로그인 실패시 redirect할 url.  when login failed, redirect to this url
        setDefaultFailureUrl("/formlogin?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}