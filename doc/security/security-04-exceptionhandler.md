# 401 Unauthorized와 403 Forbidden의 처리 
401 오류와 403오류의 차이는 다음과 같다. 
* 401 Unauthorized: 인증되지 않은 사용자가 인증된 사용자만 접근 가능한 페이지에 접근할 때 발생하는 오류
* 403 Forbidden: 인증된 사용자가 접근할 수 없는 페이지에 접근할 때 발생하는 오류

SpringSecurity에서 401과 403 예외가 발생했을 때에는 exceptionHandling() 메소드를 사용하여 커스텀할 수 있다. 


## 401 Unauthorized 처리
401 예외가 발생했을 때 예외페이지로 리다이렉트하지 않고 직접처리할 수 있다. 처리할 Util 클래스를 작성한다. 
```java
package com.jirepos.core.util;

// 새로운 스프링 부트 3.0 는 Java17 을 기반으로 작성된 새로운 프레임 워크입니다.
import java.util.Optional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
// Spring framework 6와 Spring Boot 3 가 릴리즈 되었습니다.
// SpingBoot 3에서 Jakarta EE의 jakarta.* 로 변경이 되었다. 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

/**
 * Servlet 처리할 때 사용할 편리한 기능을 제공합니다.
 */
public class ServletUtils {
    /** 권한없음(401: 인증자격증명 없음) 페이지를 생성하여 반환한다. */
    public static void responseUnauthorized(@NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response) {
    
        String accept = request.getHeader("accept");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            if (accept.contains("application/json")) {
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Unauthorized\"}");
            } else if(accept.contains("text/html")) {
                response.setContentType("text/html");
                response.getWriter().write("<h1>Unauthorized</h1>");
            }else { 
                response.setContentType("text/plain");
                response.getWriter().write("Unauthorized");
            }
            // 401 에러는 전달할 에러 메시지가 없으므로 문자열을 전달한다. 
            // Content-Type은 http2.js에서 사용하므로 정확히 맞추어야 한다.
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }// :
}/// ~
```
AuthenticationEntryPoint 빈을 등록하는 메소드를 작성한다. 이 메소드에서 ServletUtils.responseUnauthorized() 메소드를 호출한다.

```java
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
```

SecurityFilterChain 빈을 등록하는 메소드에서 exceptionHandling() 메소드를 사용하여 인증실패시 처리할 AuthenticationEntryPoint 빈을 전달한다. 

```java
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER)  // 순서가 filterStaticResources() 뒤에 오도록 순서를 지정해야 함 
/**
 * 정적자원을 제외한 모든 요청에 대한 보안규칙을 정의한다.
 */
public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
  // ... 생략 ...
  // 인증 실패(401)과 권한없음(403) 처리 커스터마이징 
  http.exceptionHandling(exceptionHandlingCustomizer ->  exceptionHandlingCustomizer
      .authenticationEntryPoint(authenticationEntryPoint()); // 인증 실패시 401
  return http.build();
}//:
```


## 403 Forbidden 처리

403 예외가 발생했을 때 예외페이지로 리다이렉트하는 AccessDeniedHandler 빈을 정의한다. 

```java
@Bean
public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, e) -> {
        response.sendRedirect("/static/403.html");
    };
}  
```    
앞에서 생성한 AccessDeniedHandler 빈을 accessDeninedHandler() 메소드에 전달한다. 
```java
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER)  
public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
    // 인증 실패(401)과 권한없음(403) 처리 커스터마이징 
    http.exceptionHandling(exceptionHandlingCustomizer ->  exceptionHandlingCustomizer
        .authenticationEntryPoint(authenticationEntryPoint()) // 인증 실패시 401
        .accessDeniedHandler(accessDeniedHandler())); // 인가(권한) 실패시
    return http.build();
  }//:
```  

서버의 자원에 접근했을 때 로그인이 되어 있지 않으면 401 Unauthorized 를 표시하는 화면을 볼 수 있을 것이다. 아직 로그인을 하지 않았으므로 403.html 페이지는 볼 수 없을 것이다.



