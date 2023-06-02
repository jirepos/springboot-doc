package com.kyoofus.basicauth.filter;

import java.io.IOException;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

  public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    log.info(">>>>>>>>>>>>>>>>> CustomBasicAuthenticationFilter");
    super.doFilterInternal(request, response, chain);
  }

}
