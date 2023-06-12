package com.kyoofus.security.config;

import com.kyoofus.security.handlers.AuthFailHandler;
import com.kyoofus.security.handlers.AuthSuccessHandler;
import com.kyoofus.security.oauth2.services.CustomOAuth2UserService;
import com.kyoofus.security.oauth2.services.CustomOidcUserService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class ConfigOAuthAuthentication {

    /** OAuth2, OIDC 로그인 처리 */
    public static  void oidcLogin(HttpSecurity http
            , CustomOAuth2UserService customOAuth2UserService
            , CustomOidcUserService customOidcUserService
            , AuthSuccessHandler authSuccessHandler
            , AuthFailHandler authFailHandler
    ) throws Exception {
        http.oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                // SpringSecurity 6.1에서는 메소드가 나눠짐
                                .oidcUserService(customOidcUserService) // OIDC 사용자 서비스
                                .userService(customOAuth2UserService) // OAuth2 사용자 서비스
                        )
                        // 로그인 성공 시 추가적인 처리를 위해 AuthenticationSuccessHandler를 구현
                        // .successHandler( new AuthenticationSuccessHandler(){
                        // @Override
                        // public void onAuthenticationSuccess(HttpServletRequest request,
                        // HttpServletResponse response, Authentication authentication) throws
                        // IOException, ServletException {
                        // System.out.println("authentication : " + authentication.getName());
                        // response.sendRedirect("/home"); // 인증이 성공한 후에는 root로 이동
                        // }
                        // })
                        .successHandler(authSuccessHandler) // OAuth2 로그인 성공 후 처리, 05.31 동작하지 않음. 원인 파악중.
                        // .failureHandler(new AuthenticationFailureHandler() {
                        //   @Override
                        //   public void onAuthenticationFailure(HttpServletRequest request,
                        //       HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        //     System.out.println("exception : " + exception.getMessage());
                        //     response.sendRedirect(LOGIN_FAIL_URL); // 인증이 실패한 후에는 /loginfail로 이동
                        //   }
                        .failureHandler(authFailHandler) // OAuth2 로그인 실패 후 처리
                // .defaultSuccessUrl("/home", true) // OAuth2 로그인 성공 후 이동할 URL
                // .failureUrl("/loginfail") // OAuth2 로그인 실패 후 이동할 URL. id가 없거나 비밀번호가 틀린 경우에는
                // OAuth 인증화면에서 진행이 되지 않아 실패페이지로 이동되지 않음
        );
    }
}///~
