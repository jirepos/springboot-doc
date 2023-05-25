# Logout

Spring Security에서 default logout을 사용하려면 다음과 같이 withDefaults()와 함께 logout() 메소드를 사용한다. 

```java
import static org.springframework.security.config.Customizer.withDefaults;
// logout url은 /logout, logout success url은 /login?logout 
http.logout(withDefaults()); // SpringSecurity default logout
```

* 로그 아웃의 디폴트 url :  /logout 
* 로그아웃 후 리다이렉트 url : /login?logout



## 로그아웃 커스터마이징
디폴트 로그아웃과 마찬가지로 logout() 메소드를 사용하여 로그아웃을 커스텀할 수 있다. 
```java
// logout 
http.logout(customizer -> customizer
    .logoutUrl("/logout") // default logout url은 /logout
    .logoutSuccessUrl("/formlogin") // default logout success url은 /login?logout
    .invalidateHttpSession(true) // 로그아웃시 세션을 무효화한다. 
    .deleteCookies("JSESSIONID") // 로그아웃시 쿠키를 삭제한다.
); 
```    


* logoutUrl : 로그아웃 url을 설정한다. 디폴트는 /logout이다.
* logoutSuccessUrl : 로그아웃 후 리다이렉트 url을 설정한다. 디폴트는 /login?logout이다.
* invalidateHttpSession : 로그아웃시 세션을 무효화한다. 디폴트는 true이다.
* deleteCookies : 로그아웃시 삭제할 쿠키를 설정한다. 디폴트는 없다.











