package com.kyoofus.security.config;

import com.kyoofus.security.custom.CustomUsernamePassordProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// @EnableWebSecurity
// @Configuration
// @Slf4j
@RequiredArgsConstructor
/**  AuthenticationProvider 만 커스텀하여 사용하는 예제 */
// CustomUsernameProvider 사용
public class TestCustomUsernamePasswordAuthConfig {

    @Autowired
    // @Autowired 사용하지 말라고 경로하고 있으나 CustomUsernameProvider가 @requiredArgsConstructor를 사용하고 있어도
    // 제 때 주입이 안되 오류 발생하여 사용
    private CustomUsernamePassordProvider customUsernamePassordProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // Provider 설정
        authenticationManagerBuilder.authenticationProvider(customUsernamePassordProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain two(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/public/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.httpBasic(withDefaults());
        return http.build();
    }// :
}///~
