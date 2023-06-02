package com.kyoofus.basicauth.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFilter extends GenericFilterBean {

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    log.info(">>>>>>>>>>>>>>>>> CustomFilter" + LocalDateTime.now().toString());
    chain.doFilter(request, response);
  }
}