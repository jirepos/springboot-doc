package com.kyoofus.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kyoofus.basicauth.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

// @Component
// @Configuration
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

  // private final CustomUserDetailService customUserDetailService;
  // private final PasswordEncoder passwordEncoder;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    // UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

    // if (!passwordEncoder.matches(password, userDetails.getPassword())) {
    //   throw new BadCredentialsException(username + "Invalid password");
    // }

    return null; 
    // return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
  }

  // 만약 provider의 supports 메소드의 리턴값을 항상 true로 해둔다면, 다른 인증방식을 수행할때도 provider가 동작하게
  // 될 것이다. 따라서 이번 실습은 특정 CustomAuthenticationToken을 사용하는 경우에만 provider가 동작되게
  // 설정했다.
  @Override
  public boolean supports(Class<?> authentication) {
    return CustomAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
