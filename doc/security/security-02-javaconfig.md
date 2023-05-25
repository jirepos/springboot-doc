# Java Configuration 

SpringSecurity를 활성화하기 위해서 @EnableWebSecurity과 @Configuration 어노테이션을 붙여서 SecurityConfig 클래스를 생성하고 아래 소스를 참고하여 클래스의 뼈대를 작성한다. 
* 스프링시큐리티를 활성화하려면 PasswordEncoder 빈을 등록해야 한다.  @Bean 어노테이션을 붙여서 PasswordEncoder 빈을 등록한다. 
* SecurityFilterChain 빈을 등록한다. 


```java
package com.jirepos.autoconfig.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity // Spring Security를 활성화 시킨다. 
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
  public SecurityFilterChain filterStaticResources(HttpSecurity http) throws Exception {
    return http.build();
  }//:

}///~

```