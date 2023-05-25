package com.jirepos.autoconfig.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import com.jirepos.core.util.ServletUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity // Spring Security를 활성화 시킨다.
@Configuration
// final 필드에 대해 생성자를 만들어주는 lombok의 annotation.
// 새로운 필드를 추가할 때 다시 생성자를 만드는 번거로움을 없앨 수 있다. ( @Autowired 사용하지 않고 의존성 주입 )
@RequiredArgsConstructor
/**
 * SpringSecurity 설정을 위한 Java Configuration 클래스이다.
 */
public class SecurityConfig {

  /** 정적자원 */
  private static final String[] STATIC_RESOURCES_WHITELIST = {
      "/static/**",
      "/resources/**",
      "/public/**"
  };

  /** static 자원이외의 허용할 url */
  private static final String[] AUTH_WHITELIST = {
      // this app
      "/api/v1",
      // "/noauth/**",
      // "/formlogin",
      // "/login",
      // "/logout" ,
      // // -- swagger ui
      // "/swagger-resources/**",
      // "/swagger-ui.html",
      // "/swagger-ui/**",
      // "/v3/api-docs/**",
      // "/v3/api-docs",
      // "/webjars/**"
  };

  /**
   * 패스워드를 암호화하기 위해서 사용한다. 동일한 방식으로 암호화를 해야 비교할 수 있다.
   * UserDetailService를 구현한 클래스에 주입하기 위해서 @Bean으로 생성한다.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    // BCrypt가 가장 많이쓰이는 해싱 방법
    // 패스워드 인크립트할 때 사용
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
  /**
   * 정적자원에 대한 보안규칙을 정의한다.
   */
  public SecurityFilterChain filterStaticResources(HttpSecurity http) throws Exception {
    // 정적자원만 처리
    http.securityMatcher(STATIC_RESOURCES_WHITELIST) // AUTH_STATIC_WHITELIST 에 설정한 URL 에서만 SpringSecurity 가 동작
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll() // 모든 접근 허용
        );
    return http.build();
  }// :

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER) // 순서가 filterStaticResources() 뒤에 오도록 순서를 지정해야 함
  /**
   * 정적자원을 제외한 모든 요청에 대한 보안규칙을 정의한다.
   */
  public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
    // 권한 설정
    http
        .securityMatcher("/**", "") // filterStaticResources()에서 처리하지 않는 모든 URL에서 SpringSecurity가 동작
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/noauth").permitAll() // 인증하지 않는다.
            .anyRequest().authenticated());

    //
    http.csrf(customizer -> customizer.disable()); // csrf 사용하지 않음

    // 로그인
    // http.formLogin(withDefaults()); // SpringSecurity의 default login form이 표시된다.
    // http.logout(withDefaults()); // SpringSecurity default logout, logout url은
    // 커스텀 폼 로그인
    http.formLogin(formLoginCustomizer -> formLoginCustomizer
        .loginPage("/formlogin").permitAll(true) // default loing url과 동일한 url 사용하면 안된다.
        // 로그인 처리 URL, default URL은 /login, loginPage()를 수정했으면 이것을 지정해야 함
        // username, password가 전달될 url
        .loginProcessingUrl("/login_proc").permitAll(true)
        .defaultSuccessUrl("/home", true).permitAll(true) // 로그인 성공 후에 랜딩 페이지
        .failureUrl("/login-fail") // 인증 실패 시 url
        // .successHandler(
        //   new AuthenticationSuccessHandler(){
        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request,
        //         HttpServletResponse response, Authentication authentication) throws
        //         IOException, ServletException {
        //         System.out.println("authentication : " + authentication.getName());
        //         response.sendRedirect("/"); // 인증이 성공한 후에는 root로 이동
        //     }
        //   }
        // )
        // .failureHandler(new AuthenticationFailureHandler(){
        //   @Override
        //   public void onAuthenticationFailure(HttpServletRequest request,
        //       HttpServletResponse response, AuthenticationException exception) throws
        //       IOException, ServletException {
        //       System.out.println("exception : " + exception.getMessage());
        //       response.sendRedirect("/login"); // 인증이 실패한 후에는 login으로 이동
        //   }
        // })
    );
    // logout
    http.logout(customizer -> customizer
        .logoutUrl("/logout") // default logout url은 /logout
        .logoutSuccessUrl("/formlogin") // default logout success url은 /login?logout
        .invalidateHttpSession(true) // 로그아웃시 세션을 무효화한다.
        .deleteCookies("JSESSIONID") // 로그아웃시 쿠키를 삭제한다.
    );


    this.configHeaders(http);
    this.configSession(http);

    // 인증 실패(401)과 권한없음(403) 처리 커스터마이징
    // http.exceptionHandling(exceptionHandlingCustomizer ->
    // exceptionHandlingCustomizer
    // // .authenticationEntryPoint(authenticationEntryPoint()) // 인증 실패시 401, 디폴트는
    // login 페이지로 리다이렉트
    // .accessDeniedHandler(accessDeniedHandler())); // 403 인가(권한) 실패시
    return http.build();
  }// :

  // https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
  /** 세션 설정 */
  private void configSession(HttpSecurity http) throws Exception  {
    http.sessionManagement(sm -> sm 
        .sessionFixation( (sessionFixation) -> sessionFixation 
            .newSession()          // 로그인 후 세션을 새로 생성
            // .none()             // 로그인 후 세션을 생성하지 않는다. 로그인 전 세션아이디 그대로 사용
            // .migrateSession()   // 새 세션을 만들고 모든 기존 세션 속성을 새 세션에 복사한다. 
            // .changeSessionId()  // 이는 Servlet 3.1 이상 컨테이너의 기본값입니다.
            
         ) // 세션 고정 보호
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성 전략
        .maximumSessions(1) // 최대 세션 수
        .maxSessionsPreventsLogin(false) // true면 기존 세션 만료, false면 신규 로그인 사용자 인증 실패
        .expiredUrl("/expired") // 세션이 만료되면 이동하는 페이지를 지정한다.
    );
  }

  /**
   * 보안 헤더 설정
   */
  private void configHeaders(HttpSecurity http) throws Exception {
    http.headers(headers -> headers 
        .frameOptions(frameOptions -> frameOptions
            // .sameOrigin() // X-Frame-Options: SAMEORIGIN
            .disable() // X-Frame-Options: DENY
        )
        .xssProtection(xss -> xss
           .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
        )
				.contentSecurityPolicy(csp -> csp
					.policyDirectives("script-src 'self' http://example.com; object-src https://example.com; ")
				)   
        // 아래과 같이 설정했으나 헤더에서 확인되지 않았다.      
				.httpStrictTransportSecurity(hsts -> hsts
					.includeSubDomains(true)  // includeSubDomains : 서브 도메인도 HTTPS를 사용하도록 유도한다.
					.preload(true) // preload : HSTS preload list에 등록되어 있으면 HTTPS를 사용하지 않는 경우에도 HTTPS로 접속하도록 유도한다. 
					.maxAgeInSeconds(31536000) // max-age : HTTPS를 사용하는 시간을 초 단위로 지정한다. 
				)        
    );
  }//:

  /**
   * 인증 실패시 처리. filterChain()의 http.authorizeRequests()에서 인증정보가 없으면 여기서 처리한다.
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    // 로그인페이지 이동, 401오류 코드 전달
    return (request, response, e) -> {
      ServletUtils.responseUnauthorized(request, response);
    };
  }

  /**
   * 권한 없음 처리.
   * 
   * 403 에러는 접근 권한 없는 url 요청 시 반환되는 응답코드이다. 자격증명(즉, 세션 같은 것)은 존재한다.
   * 만약, 403에러 페이지가 아닌 다른 처리를 하고 싶다면 AccessDeniedHandler Bean을 생성한다.
   * 
   * HttpSecurity.exceptionHandling()을 해줘야 한다.
   */
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, e) -> {
      response.sendRedirect("/static/403.html");
    };
  }
}/// ~

