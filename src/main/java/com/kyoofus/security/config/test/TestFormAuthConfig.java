package com.kyoofus.security.config.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
// @EnableWebSecurity
// @Configuration
// @Slf4j
@RequiredArgsConstructor
/** Form 인증 Authentication 설정 */
public class TestFormAuthConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain two(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/public/**").permitAll() // 인증하지 않는다.
                        .anyRequest().authenticated()
                );
        http.formLogin(withDefaults()); // SpringSecurity의 default login form이 표시된다.
        http.csrf(customizer -> customizer.disable()); // csrf 사용하지 않음
        return http.build();
    }// :
}///~