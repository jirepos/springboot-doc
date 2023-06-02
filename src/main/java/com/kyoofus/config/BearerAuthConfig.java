package com.kyoofus.config;

import com.kyoofus.basicauth.filter.CustomBasicAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Slf4j
@RequiredArgsConstructor
/** Basic Authentication 설정 */
public class BearerAuthConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain two(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/public/**").permitAll() // 인증하지 않는다.
                        .anyRequest().authenticated()
                );
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        // CustomBasicAuthenticationFilter 를 추가한다.
        http.addFilterBefore(new CustomBasicAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
        return http.build();

    }// :
}///~
