package com.kyoofus.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;

public class ConfigExceptionHandling {

    /**
     * 권한 없음 처리.
     * 403 에러는 접근 권한 없는 url 요청 시 반환되는 응답코드이다. 자격증명(즉, 세션 같은 것)은 존재한다.
     * 만약, 403에러 페이지가 아닌 다른 처리를 하고 싶다면 AccessDeniedHandler Bean을 생성한다.
     * HttpSecurity.exceptionHandling()을 해줘야 한다.
     */
    public static AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            response.sendRedirect("/static/403.html");
        };
    }


    /** 예외처리 핸들링 */
    public static  void exceptionHandling(HttpSecurity http) throws Exception {
        // 인증 실패(401)과 권한없음(403) 처리 커스터마이징
        http.exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
                // .authenticationEntryPoint(authenticationEntryPoint()) // 인증 실패시 401, 디폴트는
                // login 페이지로 리다이렉트
                .accessDeniedHandler(accessDeniedHandler())); // 403 인가(권한) 실패시
    }

}///~
