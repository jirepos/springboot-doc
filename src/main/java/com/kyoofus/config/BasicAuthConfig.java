package com.kyoofus.config;

import com.kyoofus.basicauth.auth.MyBasicAuthenticationEntryPoint;
import com.kyoofus.basicauth.filter.CustomBasicAuthenticationFilter;
import com.kyoofus.basicauth.filter.CustomFilter;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//import static org.springframework.security.config.Customizer.withDefaults;
//@EnableWebSecurity
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
/** Basic Authentication 설정 */
public class BasicAuthConfig {
//    private final MyBasicAuthenticationEntryPoint myAuthenticationEntryPoint;
//    private final AuthenticationConfiguration authenticationConfiguration;
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Order(SecurityProperties.BASIC_AUTH_ORDER)
//    public SecurityFilterChain two(HttpSecurity http) throws Exception {
//        http.securityMatcher("/**")
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/app/public/**").permitAll() // 인증하지 않는다.
//                        .anyRequest().authenticated()
//                );
//        // AuthenticationManager 구한다.
//        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
//        // CustomBasicAuthenticationFilter 를 추가한다.
//        http.addFilterBefore(new CustomBasicAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class);
//        // CustomBasicAuthenticationFilter 뒤에 CustomFilter 를 추가한다.
////        http.addFilterAfter(new CustomFilter(), CustomBasicAuthenticationFilter.class);
//
//        // http.httpBasic(withDefaults()); // 디폴트 사용
//        http.httpBasic(customizer -> customizer
//                        .realmName("my-basic-realm")
////                        .authenticationDetailsSource(null)
////        .authenticationEntryPoint(myAuthenticationEntryPoint) // 인증 실패 이후 처리  인증 실패시 401
//        );
//        http.csrf(customizer -> customizer.disable()); // csrf 사용하지 않음
//        return http.build();
//    }// :
}///~