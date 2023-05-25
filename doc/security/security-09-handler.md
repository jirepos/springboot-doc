# Handler 

## 로그인 성공시 처리 
로그인이 성공했을 때 추가적인 로직을 처리할 경우가 있다. 이런 경우 AuthenticationSuccessHandler 인터페이스를 구현하면 되는데, 주의할 점은 redirect 처리를 해야 한다. 

```java
http.formLogin(formLoginCustomizer -> formLoginCustomizer
    .loginPage("/formlogin").permitAll(true) // default loing url과 동일한 url 사용하면 안된다.
    // 로그인 처리 URL, default URL은 /login, loginPage()를 수정했으면 이것을 지정해야 함
    // username, password가 전달될 url
    .loginProcessingUrl("/login_proc").permitAll(true)
    .defaultSuccessUrl("/home", true).permitAll(true) // 로그인 성공 후에 랜딩 페이지
    .failureUrl("/login-fail") // 인증 실패 시 url
    .successHandler(
      new AuthenticationSuccessHandler(){
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws
            IOException, ServletException {
            System.out.println("authentication : " + authentication.getName());
            response.sendRedirect("/"); // 인증이 성공한 후에는 root로 이동
        }
      }
    )
);
```    

다른 방법으로는 SimpleUrlAuthenticationSuccessHandler 클래스를 상속받아 구현하는 방법이 있다.

```java
package com.jirepos.oauth2.handlers;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**  로그인 성공시 처리하는 핸들러이다. */
// @Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    public AuthSuccessHandler(String targetUrl) {
        super();
        setDefaultTargetUrl(targetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        // 보통 UserDetails를 상속받아 권한을 관리하는데 이때 컨트롤러 단에서 Authentication 객체를 받으면 Principal을 받아 쓸 수 있다.
        // what the getPrincipal() returns depends on the authentication provider
        // Object o = authentication.getPrincipal();
        
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
```

Configuration 클래스에 private 필드로 정의하여 사용한다. 
```java
@Autowired
private AuthSuccessHandler authSuccessHandler;

public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
  http.formLogin(formLoginCustomizer -> formLoginCustomizer
      .loginPage("/formlogin").permitAll(true) 
      // ... 
      .successHandler(authSuccessHandler) 
  );
}
```    

## 로그인 실패 시 처리 
로그인이 실패했을 때 추가적인 로직을 처리할 경우가 있다. 이런 경우 AuthenticationFailureHandler 인터페이스를 구현하면 되는데, 주의할 점은 redirect 처리를 해야 한다. 
```java
http.formLogin(formLoginCustomizer -> formLoginCustomizer
    .failureHandler(new AuthenticationFailureHandler(){
      @Override
      public void onAuthenticationFailure(HttpServletRequest request,
          HttpServletResponse response, AuthenticationException exception) throws
          IOException, ServletException {
          System.out.println("exception : " + exception.getMessage());
          response.sendRedirect("/login"); // 인증이 실패한 후에는 login으로 이동
      }
    })
);
```



다른 방법으로는 SimpleUrlAuthenticationFailureHandler 클래스를 상속받아 구현하는 방법이 있다. 

```java
/**
 * 로그인 실패시 처리하는 핸들러입니다.  when login failed, this handler will be called.
 */
@Component
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // exception.getMessage() shows the error message 
        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            // 로그인 실패
        } else if(exception instanceof LockedException) {
            // 계정 잠김
        } else if(exception instanceof DisabledException) {
            // 계정 비활성화
        } else if(exception instanceof AccountExpiredException) {
            // 계정 만료
        } else if(exception instanceof CredentialsExpiredException) {
            // 비밀번호 만료
        }
        // 로그인 실패시 redirect할 url.  when login failed, redirect to this url
        setDefaultFailureUrl("/formlogin?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
```



## 로그아웃 핸들러 
로그아웃 처리는 로그인 처리와 비슷하다. 로그아웃 처리를 하기 위해서는 logout() 메소드를 호출하면 된다.

```java
http.logout(customizer -> customizer
  .addLogoutHandler(new LogoutHandler() { // 로그아웃 처리를 위한 핸들러
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        // 로그아웃 처리를 위한 로직
    }
  })
);   
```




