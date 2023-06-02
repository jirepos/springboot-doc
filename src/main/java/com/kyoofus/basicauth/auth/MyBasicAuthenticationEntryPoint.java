package com.kyoofus.basicauth.auth;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 기본적 으로 Spring Security에서 제공 하는 BasicAuthenticationEntryPoint 는 401 Unauthorized 응답에 대한 전체 페이지를 클라이언트에 다시 반환합니다. 
// 오류에 대한 이 HTML 표현은 브라우저에서 잘 렌더링됩니다. 반대로 json 표현이 선호될 수 있는 REST API와 같은 다른 시나리오에는 적합하지 않습니다.
// https://www.baeldung.com/spring-security-basic-authentication
@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException authException)
      throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    final PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 - " + authException.getMessage());
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("Baeldung");
    super.afterPropertiesSet();
  }

}