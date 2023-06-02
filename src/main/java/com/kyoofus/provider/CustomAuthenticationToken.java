package com.kyoofus.provider;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// spring security에 기본적으로 제공하는 token이 존재한다. (예: AnonymousAuthenticationToken, UsernamePasswordAuthenticationToken)
// spring security에서 제공하는 token 외에 추가적인 필드들을 가져야하는 경우 별도의 token을 생성해야 한다
public class CustomAuthenticationToken extends AbstractAuthenticationToken {
  private String email;
  private String credentials;

  public CustomAuthenticationToken(String email, String credentials) {
    // SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.ADMIN.getValue());  // ADMIN
    super(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) );
    this.email = email;
    this.credentials = credentials;
  }

  public CustomAuthenticationToken(String email, String credentials, Collection<? extends GrantedAuthority> authorities) {
      super(authorities);
      this.email = email;
      this.credentials = credentials;
  }

  public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
      super(authorities);
  }

  @Override
  public Object getCredentials() {
      return this.credentials;
  }

  @Override
  public Object getPrincipal() {
      return this.email;
  }
}