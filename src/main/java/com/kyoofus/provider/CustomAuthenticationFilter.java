package com.kyoofus.provider;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
      super(requiresAuthenticationRequestMatcher);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
      String email = request.getParameter("username");
      String credentials = request.getParameter("password");

      return getAuthenticationManager().authenticate(new CustomAuthenticationToken(email, credentials));
  }
}