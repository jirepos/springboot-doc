package com.kyoofus.security.config.test;

import com.kyoofus.security.custom.CustomSecurityFilter;
import com.kyoofus.security.custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// @EnableWebSecurity
// @Configuration
// @Slf4j
// @RequiredArgsConstructor
/** Custom Authentication 설정 */
public class TestCustomSecurityFilterConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
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
        AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager();
        http.addFilterBefore(new CustomSecurityFilter(authManager), BasicAuthenticationFilter.class);
        return http.build();
    }// :

//
//     @Bean
//     @Order(SecurityProperties.BASIC_AUTH_ORDER)
//     public SecurityFilterChain two(HttpSecurity http, AuthenticationManager authManager) throws Exception {
// //        처리할 URL Pattern 설정
//         http.securityMatcher("/**")
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/app/public/**").permitAll() // 인증하지 않는다.
//                         .anyRequest().authenticated()
//                 );
// //        AuthenticationManager 인스턴스를 구한다.
// //        AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager();
// //        커스텀 필터를 구현하여 BasicAuthenticationFilter 앞에 인스턴스를 생성하여 추가한다.
// //        http.addFilterBefore(new CustomFilter(authenticationManager), BasicAuthenticationFilter.class);
//
// //        http.authenticationProvider(new CustomProvider());  // 이 설정은 provider를 못찾음. 원인 파악 필요
// //        http.authenticationManager(authManager); // 이 설정은 manager를 못 찾음
//         http.addFilterBefore(new CustomFilter(authManager), BasicAuthenticationFilter.class);
//         return http.build();
//     }// :
//     @Bean
//     public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//         AuthenticationManagerBuilder authenticationManagerBuilder =
//                 http.getSharedObject(AuthenticationManagerBuilder.class);
//         authenticationManagerBuilder.authenticationProvider(customAuthProvider);
//         authenticationManagerBuilder.authenticationProvider(new CustomProvider());
// //        authenticationManagerBuilder.inMemoryAuthentication()
// //                .withUser("memuser")
// //                .password(passwordEncoder().encode("pass"))
// //                .roles("USER");
//         return authenticationManagerBuilder.build();
//     }
}///~
