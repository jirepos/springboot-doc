package com.kyoofus.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** 다중 SecurityFilterChain 테스트 자바 구성 파일이다. */
@EnableWebSecurity // Spring Security를 활성화 시킨다.
@Configuration
@RequiredArgsConstructor
public class MultipleSecurityConfig  extends  MultipleSecurityBaseConfig{

    /** 정적자원에 대한 보안규칙을 정의한다.  */
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER  -1  )
    public SecurityFilterChain filterFirstStaticResources(HttpSecurity http) throws Exception {
        // 정적자원만 처리
        http.securityMatcher(STATIC_RESOURCES_WHITELIST) // AUTH_STATIC_WHITELIST 에 설정한 URL 에서만 SpringSecurity 가 동작
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 접근 허용
                );
        return http.build();
    }// :

}///~
