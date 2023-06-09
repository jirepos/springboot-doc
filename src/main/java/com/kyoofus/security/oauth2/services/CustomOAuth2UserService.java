package com.kyoofus.security.oauth2.services;


import com.kyoofus.core.util.ServletUtils;
import com.kyoofus.security.formlogin.bean.FormSessionUser;
import com.kyoofus.security.oauth2.attrs.OAuthAttributes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/** OAuth2 로그인 시 로그인 처리 서비스 클래스이다. */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 표준 OAuth 2.0 제공자를 지원하는 OAuth2UserService 구현체
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // ClientRegistration 은 OAuth 2.0 또는 OpenID Connect 1.0 공급자에 대한 클라이언트 등록
        // applicaiton.yml의  spring.security.oauth2.client.registration.google이 registrationID가 된다. 
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        // 사용자를 식별하는 유니크한 아이디 값을 담고 있는 식별자를 참조하는 UserInfo 응답에 반환된 속성의 이름
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // OAuth2User의 attribute를 담을 클래스.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 다음과 같이 속성값을 구할 수 있음  
        System.out.println((String) oAuth2User.getAttributes().get("name"));
        System.out.println((String) oAuth2User.getAttributes().get("email"));
        System.out.println((String) oAuth2User.getAttributes().get("picture")); 

        saveCustomUserInfoToSession(userRequest, oAuth2User);

        //  OAuth2User를 생성하여 반환한다. 
        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) 
            , attributes.getAttributes() 
            , attributes.getNameAttributeKey()  // getAttributes()에서 사용자의 "name"을 접근하기 위해 사용하는 키 
            );
    }
    

    /** 세션에 추가적인 사용자 정보를 담는다.  */
    private void saveCustomUserInfoToSession(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // 세션에 담을 추가적인 사용자 정보 
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // DB에서 사용자 정보를 조회할 키 값 
        String email = (String)attributes.get("email");
        // DB에서 사용자 조회는 생략 

        FormSessionUser sessUser = new FormSessionUser();
        sessUser.setName((String)attributes.get("name"));
        sessUser.setEmail((String)attributes.get("email"));
        sessUser.setPicture((String)attributes.get("picture"));
        // DB에서 조회한 정보를 여기서 설정 

        // 세션에 저장한다. 
        Optional<HttpServletRequest> request = ServletUtils.getRequest();
        if(!request.isEmpty()){ 
            HttpServletRequest req = request.get();
            req.getSession().setAttribute("user", sessUser);
        }
    }

}///~