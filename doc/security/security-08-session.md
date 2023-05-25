# 세션 관리 

처음 사이트에 접속하면 sessionId key(이하 key)를 발급 받는데, 이 key를 이용하여 로그인 하고, 서비스를 이용하게된다. 이 경우 세션키를 누군가가 탈취하게 되면 보안에 문제가 생긴다. 로그인 후 sessionId를 새로 발급해주면 해결된다. 최초 접속하여 세션이 발급되는 지점에서 기존 세션아이디가 있으면 삭제하고 재발급하면 된다. sessionFixationProtectionStrategy를 Filter에 적용해야 한다.

Session Fixation Protection에 대한 전략은 세 가지 권장 옵션 중에서 선택하여 제어할 수 있다. 

* **changeSessionId** - 새 세션을 만들지 마라. 대신 서블릿 컨테이너에서 제공하는 세션 고정 보호를 사용하하라. Servlet 3.1 (Java EE 7) and newer containers에서만 사용가능하다. 이전 버전에서는 예외 발생한다. 이는 Servlet 3.1 이상 컨테이너의 
* **newSession** -로그인 후 세션을 새로 생성한다.세션 유지를 위해 세션 아이디를 변경한다. 인증에 성공할 때마다 새로운 세션을 생성하고, 새로운 JSESSIONID를 발급. 서블릿 3.1에서 기본값
* **none** - 로그인 후 세션을 생성하지 않는다. 로그인 전 세션아이디 그대로 사용


 Servlets 3.0 또는 그 이전 버전의 경우 "migrateSession" 값이 기본으로 설정된다. Servlets 3.1 이상이면 "changeSessionId" 값이 설정되어 진다. Servlets 3.1 부터 HttpServletRequet 객체에서 제공되는 changeSessionId() 메소드가 사용되어 지는 것이다. 이 두가지 방식은 모두 세션아이디는 변경하고, 세션의 내용을 그대로 유지방식으로 동작한다.

```java
    http.sessionManagement(sm -> sm 
        // https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
        .sessionFixation( (sessionFixation) -> sessionFixation 
            .newSession()          // 로그인 후 세션을 새로 생성한다. 기본값.
            // .none()             // 로그인 후 세션을 생성하지 않는다. 
            // .migrateSession()   // 새 세션을 만들고 모든 기존 세션 속성을 새 세션에 복사한다.. 
            // .changeSessionId()  // Servlet 3.1 이상 컨테이너의 기본값입니다.
         ) // 세션 고정 보호
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성 전략
        .maximumSessions(1) // 최대 세션 수
        .maxSessionsPreventsLogin(false) // true면 기존 세션 만료, false면 신규 로그인 사용자 인증 실패
        .expiredUrl("/expired") // 세션이 만료되면 이동하는 페이지를 지정한다.
    );
```    

**SessionCreationPolicy**  
Spring Security에서 SessionCreationPolicy는 세션 생성 정책을 설정하는 데 사용되는 옵션이다. 세션은 클라이언트와 서버 간의 상태를 유지하기 위해 사용되는 메커니즘이다. Spring Security는 세션을 사용하여 인증 및 인가 정보를 유지하고 관리한다. 

* SessionCreationPolicy.ALWAYS 스프링 시큐리티가 항상 새로운 세션을 생성
* SessionCreationPolicy.NEVER 스프링 시큐리티가 새로운 세션을 생성하지 않음
* SessionCreationPolicy.IF_REQUIRED 스프링 시큐리티가 필요 시에 생성(기본값).
* SessionCreationPolicy.STATELESS 스프링 시큐리티가 새로운 세션을 생성하지 않음. 이 경우 세션을 사용하는 요청은 세션을 사용하지 않습니다.

## 참조
[Understanding Session Fixation Attack Protection](https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html)     
