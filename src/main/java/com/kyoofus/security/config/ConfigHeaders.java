package com.kyoofus.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

public class ConfigHeaders {

    /**
     * 보안 헤더 설정
     */
    public static void headers(HttpSecurity http) throws Exception {
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                        // .sameOrigin() // X-Frame-Options: SAMEORIGIN
                        .disable() // X-Frame-Options: DENY
                )
                .xssProtection(xss -> xss
                        .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("script-src 'self' http://example.com; object-src https://example.com; "))
                // 아래과 같이 설정했으나 헤더에서 확인되지 않았다.
                .httpStrictTransportSecurity(hsts -> hsts
                        .includeSubDomains(true) // includeSubDomains : 서브 도메인도 HTTPS를 사용하도록 유도한다.
                        .preload(true) // preload : HSTS preload list에 등록되어 있으면 HTTPS를 사용하지 않는 경우에도 HTTPS로 접속하도록 유도한다.
                        .maxAgeInSeconds(31536000) // max-age : HTTPS를 사용하는 시간을 초 단위로 지정한다.
                ));
    }// :

}
