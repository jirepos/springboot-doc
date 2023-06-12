package com.kyoofus.security.config;

import com.kyoofus.security.custom.CustomAuthenticationProvider;
import com.kyoofus.security.custom.CustomSecurityFilter;
import com.kyoofus.security.formlogin.service.CustomUserDetailsService;
import com.kyoofus.security.handlers.AuthFailHandler;
import com.kyoofus.security.handlers.AuthSuccessHandler;
import com.kyoofus.security.oauth2.services.CustomOAuth2UserService;
import com.kyoofus.security.oauth2.services.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * SpringSecurity 설정을 위한 Java Configuration 클래스이다.
 */
@EnableWebSecurity // Spring Security를 활성화 시킨다.
@Configuration
// final 필드에 대해 생성자를 만들어주는 lombok의 annotation.
// 새로운 필드를 추가할 때 다시 생성자를 만드는 번거로움을 없앨 수 있다. ( @Autowired 사용하지 않고 의존성 주입 )
@RequiredArgsConstructor
public class SecurityConfig extends SecurityConstatns {

    // -- OAuth
    /** OAuth2 로그인 시 로그인 처리를 위한 서비스 클래스 */
    private final CustomOAuth2UserService customOAuth2UserService;
    /** OIDC로그인 시 로그인 처리를 위한 서비스 클래스 */
    private final CustomOidcUserService customOidcUserService;
    /** 로그인 실패 처리 핸들러 */
    private final AuthFailHandler authFailHandler;
    /** 로그인 성공 처리 핸들러 */
    private final AuthSuccessHandler authSuccessHandler;

    // -- Custom Authentication
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    // DaoAuthenticationProvider를 사용하기 위한 UserDetailService
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,  DaoAuthenticationProvider authProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);  // 커스텀 인증을 위한 Provider 등록
        authenticationManagerBuilder.authenticationProvider(authProvider);  // DaoAuthenticationProvider 등록
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }


    // -- Bean Section
    /** PasswordEncoder Bean 등록    */
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCrypt가 가장 많이쓰이는 해싱 방법
        // 패스워드 인크립트할 때 사용
        return new BCryptPasswordEncoder();
    }

    // -- FilterChain Section
    /** 정적자원에 대한 보안규칙을 정의한다.  */
    @Bean
    @Order(org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER - 12 )
    public SecurityFilterChain staticResourceFilterChain(HttpSecurity http) throws Exception {
        // 정적자원만 처리
        http.securityMatcher(STATIC_RESOURCES_WHITELIST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 접근 허용
                );
        return http.build();
    }// :

    /**  커스텀 인증 보안규칙을 정의한다. */
    @Bean
    @Order(org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER - 11) // 순서가 filterStaticResources() 뒤에 오도록 순서를 지정해야 함
    public SecurityFilterChain apiFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        // AuthenticationManger를 파라미터로 주입
        http
                .securityMatcher("/app/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(CUSTOM_AUTH_WHITELIST).permitAll() // 인증하지 않는다.
                        .anyRequest().authenticated());
        ConfigCsrf.defautlCsrf(http);
        ConfigFormAuthentication.disalbeFormLogin(http);
        // AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager(); // not working
        http.addFilterBefore(new CustomSecurityFilter(authManager), BasicAuthenticationFilter.class);
        return http.build();
    }//:


    /** 정적자원과 커스텀 로그인 이외의 모든 보안규칙을 정의한다.   */
    @Bean
    @Order(org.springframework.boot.autoconfigure.security.SecurityProperties.BASIC_AUTH_ORDER - 10 ) // 순서가 filterBasic() 뒤에 오도록 순서를 지정해야 함
    public SecurityFilterChain otherFilterChain(HttpSecurity http) throws Exception {
        // 권한 설정
        http
                .securityMatcher("/**") // filterStaticResources()에서 처리하지 않는 모든 URL에서 SpringSecurity가 동작
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers(AUTH_WHITELIST).permitAll() // 인증하지 않는다.
                        .anyRequest().authenticated());
        ConfigCsrf.defautlCsrf(http);
        // ConfigFormAuthentication.disalbeFormLogin(http);
        ConfigFormAuthentication.defaultFormLoginLogout(http);
        // ConfigFormAuthentication.customFormLogin(http);
        // ConfigOAuthAuthentication.oidcLogin(http
        //         , this.customOAuth2UserService
        //         , this.customOidcUserService
        //         , this.authSuccessHandler
        //         , this.authFailHandler);
        // ConfigLogout.logout(http);
        ConfigHeaders.headers(http);
        ConfigSession.session(http);
        ConfigExceptionHandling.exceptionHandling(http);
        return http.build();
    }//:

}/// ~
