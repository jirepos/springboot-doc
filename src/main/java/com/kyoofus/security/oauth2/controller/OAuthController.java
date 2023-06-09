package com.kyoofus.security.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** OAuth2, OpenID connect 로그인 테스트를 위한 컨트롤러이다. */
@Controller
public class OAuthController {

  @GetMapping("/login/{providerType}")
  public String login(HttpServletRequest request, HttpServletResponse response, @PathVariable String providerType) {
    // /oauth2/authorization/google
    // 스프링 시큐리티에서 기본적으로 제공하는 로그인 URL
    // 로그아웃과 마찬가지로 개발자가 별도의 컨트롤러를 생성할 필요 없음
    return "redirect:/oauth2/authorization/" + providerType;
  }

}
