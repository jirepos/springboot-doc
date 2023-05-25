# Spring Security


SpringBoot 3.1에서는 Spring Security 6.1.0 버전을 사용한다. 

스프링부트 2.7에서는 WebSecurityConfigurerAdapter와 그 외 몇 가지들이 Deprecated 되었다. 기존에는 WebSecurityConfigurerAdapter를 상속받아 설정을 오버라이딩 하는 방식이었는데 바뀐 방식에서는 상속받아 
오버라이딩하지 않고 모두 Bean으로 등록을 한다. 

이 부분은 3.1에서도 변함이 없다. 

    // 공식 홈페이지를 보면, spring security 5.7이상에서 더 이상 WebSecurityConfigurerAdapter 사용을
    // 권장하지 않는다고 한다. 스프링 버전이 업데이트 됨에 따라 WebSecurityConfigurerAdapter와 그 외 몇
    // 가지들이 Deprecated 되었다. 기존에는 WebSecurityConfigurerAdapter를 상속받아 설정을 오버라이딩 하는
    // 방식이었는데 바뀐 방식에서는 상속받아 오버라이딩하지 않고 모두 Bean으로 등록을 한다.
    // SecurityFilterChain Bean 등록을 통해 해결한다.
    // ​SecurityFilterChain은 Filter 보다 먼저 실행된다.
    // Custom filter가 Spring Security Filter 보다 먼저 동작하게 하려면 application.yml에 다음과 같이 추가할 수 있다.
    // ```properties
    // spring.security.filter.order=10
    // ```
    // 이렇게 하면 Spring Security Filter 앞에 10개의 Filter를 넣을 수 있는 공간이 생긴 셈이다.

// SpringBoot 3에서 
// SecurityConfig에서 제거된 다음의 메서드를 변경해주어야 한다.
// authorizeRequests() ➔ authorizeHttpRequests()
// antMatchers() ➔ requestMatchers()
// regexMatchers() ➔ RegexRequestMatchers()



       // 특정경로 지정해서 권한을 설정할때 antMatchers, mvcMatchers가 있다.
        //   - antMatchers는 제공된 개미 패턴과 일치하는 경우에만 호출되고,
        //   - mvcMatchers는 제공된 Spring MVC 패턴과 일치할 때 호출된다.
        // 예를들면, 
        //   - antMatchers(”/info”) 하면 /info URL과 매핑 되지만
        //   - mvcMatchers(”/info”)는 /info/, /info.html 이 매핑이 가능하다.

        // authorizeRequests()는 보안을 유지해야 하는 URL과 해당 URL에 액세스할 수 있는 역할을 지정할 수 있다.
        // 예를 들어 모든 사용자가 다른 URL에 액세스하도록 허용하면서 'ADMIN' 역할을 가진 사용자에게만 URL에 대한
        // 액세스를 제한할 수 있다. http 요청에 대해서 모든 사용자가 /** 경로로 요청할 수 있지만, /member/**
        // , /admin/** 경로는 인증된 사용자만 요청이 가능하다.







        // 스프링 시큐리티가 제공하는 Logout 기능은 세션 무효화, 인증토큰 삭제, 쿠키 정보 삭제, 로그인 페이지로 리다이렉트 등이 있다.
        // Spring Security는 로그 아웃 후 사용자를 특정 URL로 리디렉션 할 수있는 가능성을 제공, 이 것을 피하려면
        // disable()을 사용하면 된다.
        // logout()은 Logout 관련 설정을 진행할 수 있는 LogoutConfigurer를 반환한다.
        // deprecated 
        // http.logout().disable(); // logout 방지
        // http.logout() // 로그 아웃을 진행
        //  .logoutUrl("/logout") // 로그아웃 URL 지정, 기본 주소는 /logout
        //  .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 경로 지정 .
        //  .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제
        //  .addLogoutHandler(null) // 로그아웃 핸들러 추가
        //  .logoutSuccessHandler(null) // 로그아웃 성공 후 핸들러 추가
        //  .invalidateHttpSession(true); // 세션을 삭제하는 것을 지정 (세션이 삭제되면 쿠키도 삭제된다)



        

## 참조 
https://docs.spring.io/spring-security/reference/whats-new.html

https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html