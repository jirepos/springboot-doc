# SpringBoot3.1 Sample Project 


이 프로젝트는 Spring 3.1에서 SpringSecurity와 SpringDoc를 사용하는 방법을 정리한다. 사용한 버전은 다음과 같다. 
* SpringBoot 3.1 
* Spring Security 6.1.0
* SpringDoc 2.1.0 

가이드 문서들은 [/doc](doc/README.md) 디렉토리에 정리해 놓았다. 


## SpringBoot 3.0에서 달라지는 점
SpringBoot는 SpringMVC 6.0을 사용한다. SpringBoot 3에서 달라지는 점들을 정리해 보았다. 


**최소 요구사항 변경**
  * Gradle 7.5
  * Groovy 4.0
  * Jakarta EE 9
  * Java 17
  * Kotlin 1.6
  * Hibernate 6.1
  * Spring Framework 6
  * AOT maven, gradle 플러그인 제공
  * native 지원 기능 확대


**SpringBoot3** 
* **Java 17기반으로 변경**
* **일부 Java EE API 지원 종료**
  * javax.* 패키지의 API는 Jakarta EE에서 관리하게 됨
    * javax.persistence.* ➔ jakarta.persistence.*
    * javax.validation.* ➔ jakarta.validation.*
    * javax.servlet.* ➔ jakarta.servlet.*
    * javax.annotation.* ➔ jakarta.annotation.*
    * javax.websocket.* ➔ jakarta.websocket.*
    * javax.transaction.* ➔ jakarta.transaction.*
    * @Inject 같은 JSR에서 지원하던 어노테이션들이 jakarta.annotation 패키지의 어노테이션으로 변경
  * JavaMail(javax.mail)
    * 내부적으로 javax.servlet.* 을 호출하는 코드가 있어  Jakarta Mail 로 변경  
    * java.time 호솬성 검증 필요 
  * Hibernate ORM 5.6.x 버전부터 hibernate-core-jakarta 사용
* redis 설정 변경
  * spring.redis → spring.data.redis
* XML이 점차적으로 Spring에서는 사라지게 될 것
* **RPC 지원 종료**
  * RMI 사용하는 코드를 OpenAPI로 변경할 것. 
* 새로운 AOT 엔진 도입
  * AOT란 Java의 컴파일 방법 중 하나로 프로그램이 실행되기 전에, 코드를 기계어로 컴파일하는 방식
* HttpMethod가 enum에서 class로 변경
* **trailing slash matching (URL에서 마지막으로 나오는 / 매칭), potentially malicious String "//" 은 잠재적인 보안 위혐이 있어 차단**
  * /user/view/ -> /user/view 
    * 기존에는 /user/view/과 /user/view 둘 다 매칭되었지만, 6.0부터는 default 옵션으로는 매칭 안해줌. 기존 방식은 404 발생 
  * /user//add -> /user/add
    * 기존에는 /user//add와 /user/add 둘 다 매칭되었지만, 6.0부터는 default 옵션으로는 매칭 안해줌.  500 에러 발생 


**SpringSecurity 6** 

* WebSecurityConfigurerAdapter 제거 
  * Spring Security 5.7 에서 Deprecated 되었는데 6에서 제거됨
  * SecurityFilterChain을 @Bean으로 등록하여 설정해야 함
* `antMatchers`, `mvcMatchers`, `regexMatchers`  제거
  * requestMatchers로 대체 하거나 AntPathRequestMatcher 사용
  * authorizeRequests() ➔ authorizeHttpRequests()
  * regexMatchers() ➔ RegexRequestMatchers()
* HttpSecurity#securityMatchers 추가 
  * URL 패턴에 따라 적용할 FilterChain을 분리할 수 있으므로 
  * 다양한 인증 방식 혼용 가능 
* OAuth2 client 
  * Client OAuthLogin() 변경
    * OAUTH2_USER(OAuth2UserAuthority), OIDC_USER(OidcUserAuthority) 로 분리
  * SAML2 코드 Deprecated. 새로 개발 중. 

* Tomcat 10, Jetty 11, Undertow 2.2.14 (undertow-servlet-jakarta도 포함)으로 업그레이드 필요
* Commons FileUpload, Tiles, FreeMarker JSP support 같은 서블릿 기반 기능이 지원 종료
* multipart file 업로드 혹은 FreeMarker template view는 StandardServletMultipartResolver 사용을 권장
* 이외에는 Rest 기반 웹 아키텍처 사용
* Spring MVC와 Spring WebFlux에서 더 이상 type 레벨에서의 @RequestMapping을 자동 탐색하지 않음
* interface의 경우에는 @RequestMapping을 붙여도 더 이상 탐색되지 않음
* 따라서 Class에 붙이거나 interface에도 사용하고 싶으면 @Controller도 붙여야 함
* spring-cloud-openfeign에서도 이것 때문에 interface레벨 @RequestMapping 지원 종료(Git Issue)

