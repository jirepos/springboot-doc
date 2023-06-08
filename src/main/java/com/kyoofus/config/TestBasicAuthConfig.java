package com.kyoofus.config;

import com.kyoofus.basicauth.auth.MyBasicAuthenticationEntryPoint;
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
/** Basic Authentication 설정 */
public class TestBasicAuthConfig {
    private final MyBasicAuthenticationEntryPoint myAuthenticationEntryPoint;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain two(HttpSecurity http) throws Exception {
//        처리할 URL Pattern 설정
        http.securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/public/**").permitAll() // 인증하지 않는다.
                        .anyRequest().authenticated()
                );
        http.httpBasic(withDefaults()); // 디폴트 사용
//         커스텀 설정
//        http.httpBasic(customizer -> customizer
//                        .realmName("my-basic-realm")
//                        .authenticationDetailsSource(null)
//        .authenticationEntryPoint(myAuthenticationEntryPoint) // 인증 실패 이후 처리  인증 실패시 401
//        );
        http.csrf(customizer -> customizer.disable()); // csrf 사용하지 않음
        return http.build();
    }// :
}///~