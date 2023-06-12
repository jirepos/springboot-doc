package com.kyoofus.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

public class ConfigSession {
    public static final String SESSINO_EXPIRED_URL = "/session-expired";

    // https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
    /** 세션 설정 */
    public static void session(HttpSecurity http) throws Exception {
        http.sessionManagement(sm -> sm
                .sessionFixation((sessionFixation) -> sessionFixation
                                .newSession() // 로그인 후 세션을 새로 생성
                        // .none() // 로그인 후 세션을 생성하지 않는다. 로그인 전 세션아이디 그대로 사용
                        // .migrateSession() // 새 세션을 만들고 모든 기존 세션 속성을 새 세션에 복사한다.
                        // .changeSessionId() // 이는 Servlet 3.1 이상 컨테이너의 기본값입니다.
                ) // 세션 고정 보호
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성 전략
                .maximumSessions(1) // 최대 세션 수
                .maxSessionsPreventsLogin(false) // true면 기존 세션 만료, false면 신규 로그인 사용자 인증 실패
                .expiredUrl(SESSINO_EXPIRED_URL) // 세션이 만료되면 이동하는 페이지를 지정한다. "/expired"
        );
    }
}
