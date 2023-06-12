package com.kyoofus.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class ConfigLogout {
    /** 로그아웃 설정 */
    public static void logout(HttpSecurity http) throws Exception {
        // oidc 로그인 사용시 , oidc 로그아웃은 되지 않음. 내부적으로 로그아웃은 정상적으로 동작
        http.logout(customizer -> customizer
                .logoutUrl(SecurityConstatns.SPRING_DEFAULT_LOGOUT_URL) // default logout url은 /logout
                .logoutSuccessUrl( SecurityConstatns.FORM_LOGIN_URL) // default logout success url은 /login?logout
                .invalidateHttpSession(true) // 로그아웃시 세션을 무효화한다.
                .deleteCookies(SecurityConstatns.JSESSIONID) // 로그아웃시 쿠키를 삭제한다.
        );
    }
}

