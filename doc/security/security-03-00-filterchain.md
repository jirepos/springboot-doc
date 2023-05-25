# Security 적용 대상 URL 설정 

SpringSecurity를 적용할 대상 URL을 설정할 수 있다.  securityMatcher("/**")와 같이 설정하면 모든 URL 요청에 Security를 적용한다. 

```java
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public SecurityFilterChain filterStaticResources(HttpSecurity http) throws Exception {
    http.securityMatcher("/**")  //  /noauth 아래에서 SpringSecurity가 동작     
      .authorizeHttpRequests(auth -> auth
          .anyRequest().authenticated()
      );
  return http.build();
}//:
```

다음과 같이 URL 패턴을 설정하면 "/blog"으로 시작하는 URL에 Security를 적용한다. 
```java
http.securityMatcher("/blog/**")  //  /noauth 아래에서 SpringSecurity가 동작  
```    

다음과 같이 설정하면 "/blog"로 시작하는 모든 URL에 Security를 적용하고,  "/blog/guest"로 시작하는 모든 URL은 인증없이 허용하고 나머지는 모두 인증을 요구한다. 

```java
http.securityMatcher("/blog/**")  //  /noauth 아래에서 SpringSecurity가 동작     
    .authorizeHttpRequests(auth -> auth
      .requestMatchers("/blog/guest/**").permitAll()  
      .anyRequest().authenticated()
    );
```        
메소드에 따른 동작은 다음과 같다. 
* authenticated()	인증된 사용자의 접근을 허용
* permitAll()	무조건 접근을 허용
* denyAll()	무조건 접근을 허용하지 않음
* rememberMe()	기억하기를 통해 인증된 사용자의 접근을 허용
* hasRole(String)	사용자가 주어진 역할이 있다면 접근을 허용


URL 패턴을 배열로 정의하고 securityMatcher() 메소드에 배열을 전달하면 배열에 정의한 URL 패턴에만 Security가 동작한다. 배열로 정의되지 않은 URL은 Security가 동작하지 않는다. 

```java
/** 정적자원  */
private static final String[] AUTH_STATIC_WHITELIST = {
  "/static/**",
  "/resources/**",
  "/public/**"
};
http.securityMatcher(AUTH_STATIC_WHITELIST)  // AUTH_STATIC_WHITELIST 에 설정한 URL 에서만 SpringSecurity 가 동작 
    .authorizeHttpRequests(auth -> auth
      .anyRequest().authenticated()
    );

```    

requestMatchers() 메소드에도 URL 패턴을 파라미터로 전달할 수 있다. 
```java
   .requestMatchers(AUTH_STATIC_WHITELIST).permitAll()  // working well 
```



## 여러 개의 SecurityFilterChain 적용 

URL에 따라 다른 규칙이 적용된 SecurityFilterChain을 적용할 필요가 있을 수 있다. 예를 들어, "/a/\*\*"로 시작하는 URL과 "/b/\*\*"로 시작하는 URL에 다른 보안 규칙을 적용하는 경우이다.  이때 SecurityFilterChain 빈이 등록되는 순서가 중요한데, 좁은 범위의 FilterChain을 먼저 적용하고, 넓은 범위를 뒤에 적용하도록 @Order 어노테이션을 사용하여 순서를 지정한다. 

@Order에는 int 값을 적용하는데 번호가 작은 것이 먼저 적용된다. 

```java
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public SecurityFilterChain filterStaticResources(HttpSecurity http) throws Exception {
  http.securityMatcher("좁은 범위의 URL 패턴") 
  //.... 
}
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER)  // 순서가 filterStaticResources() 뒤에 오도록 순서를 지정해야 함 
public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
  http.securityMatcher("넓은 범위의 URL 패턴") 
  //... 
}
``` 

아래의 경우 정적 자원을 요청하는 URL의 보안 규칙을 처리하는 FilterChain이 먼저 동작하고 그 외의 URL의 보안 규칙을 처리하는 FilterChain이 그 다음에 동작한다.

```java
/** 정적자원  */
private static final String[] AUTH_STATIC_WHITELIST = {
  "/static/**",
  "/resources/**",
  "/public/**"
};

@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public SecurityFilterChain filterStaticResources(HttpSecurity http) throws Exception {
  http.securityMatcher(AUTH_STATIC_WHITELIST)  // AUTH_STATIC_WHITELIST 에 설정한 URL 에서만 SpringSecurity 가 동작 
      .authorizeHttpRequests(auth -> auth
        .anyRequest().permitAll() // 모든 접근 허용 
      );
  return http.build();
}//:

@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER)  // 순서가 filterStaticResources() 뒤에 오도록 순서를 지정해야 함 
public SecurityFilterChain filterMain(HttpSecurity http) throws Exception {
  http.securityMatcher("/**", "")  // filterStaticResources()에서 처리하지 않는 모든 URL에서  SpringSecurity가 동작
      .authorizeHttpRequests(auth -> auth
      .requestMatchers("/api/noauth").permitAll()  // working well 
      .anyRequest().authenticated()
      );

  return http.build();
}//:
```

## 참고
[스프링 시큐리티 - 3](https://sqlimitless.tistory.com/24)