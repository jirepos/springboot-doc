package com.kyoofus.oauth2.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.kyoofus.core.util.ServletUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
/** OIDC 로그인 시 로그인 처리 서비스 클래스이다. */
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

    // Open ID Connect인 경우 User name Attribute Key 가 sub 이기 때문에 재정의함
    ClientRegistration clientRegistration = ClientRegistration
        .withClientRegistration(userRequest.getClientRegistration())
        .userNameAttributeName("sub")
        .build();

    // OpenId Connect를 사용하고 UserInfo 엔드 포인트에 요청하기 위해서는 OidcUserRequest를 사용
    OidcUserRequest oidcUserRequest = new OidcUserRequest(
        clientRegistration, userRequest.getAccessToken(), userRequest.getIdToken(),
        userRequest.getAdditionalParameters());

    // OidcUserRequest의 getAccessToken().getTokenValue()를 사용하여 Access Token을 얻을 수 있음
    String tokenValue = userRequest.getAccessToken().getTokenValue();

    Optional<HttpServletRequest> opt = ServletUtils.getRequest();
    HttpServletRequest request = opt.get();
    // Access Token을 저장해 놓아야 나중에 API 호출 시 사용할 수 있음
    request.getSession().setAttribute("accessToken", tokenValue);

    // OidcUserService를 사용하여 OidcUser를 가져옴
    OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
    OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest);

    // ID Token 가져오기 
    OidcIdToken idToken = oidcUser.getIdToken();
    System.out.println("Id Token:" + idToken.getTokenValue());

    // Id Token:org.springframework.security.oauth2.core.oidc.OidcIdToken@18c4aa01
    Map<String, Object> attributes = oidcUser.getClaims();
    attributes.forEach((k, v) -> {
      System.out.println(k + ":" + v);
    });

    return oidcUser;
  }
}
