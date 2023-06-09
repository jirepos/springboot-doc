package com.kyoofus.security.basicauth.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

// 기본적 으로 Spring Security에서 제공 하는 BasicAuthenticationEntryPoint 는 401 Unauthorized 응답에 대한 전체 페이지를 클라이언트에 다시 반환합니다. 
// 오류에 대한 이 HTML 표현은 브라우저에서 잘 렌더링됩니다. 반대로 json 표현이 선호될 수 있는 REST API와 같은 다른 시나리오에는 적합하지 않습니다.
// https://www.baeldung.com/spring-security-basic-authentication
// BasicAuthenticationFilter를 통해 인증을 시작하기 위해 ExceptionTranslationFilter에서 사용됩니다.
// 사용자 에이전트가 BASIC 인증을 사용하여 인증되면 로그아웃하려면 브라우저를 닫거나 승인되지 않은(401) 헤더를 보내야 합니다.
/**
 *  Restful API 인증 실패시 401 응답을 위한 커스텀 BasicAuthenticationEntryPoint
 */
@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException authException)
      throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    final PrintWriter writer = response.getWriter();
    writer.println("HTTP Status 401 - " + authException.getMessage());
  }

  @Override
  public void afterPropertiesSet() {
    // realm은 아래와 같이 해설 형식으로 구성되어 사용자가 권한에 대한 범위를 이해하는 데 도움이 되어야 한다.
    // 아래 예처럼 품질 관리 그룹인 경우 그에 해당하는 정보만 열람이 가능하다.
    // WWW-Authenticate: Basic realm="Quality Level 1"
    setRealmName("Baeldung");
    super.afterPropertiesSet();
  }

}