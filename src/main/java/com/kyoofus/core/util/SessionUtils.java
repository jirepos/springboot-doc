package com.kyoofus.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;


/**
 * 세션 유틸리티 클래스이다.
 */
public class SessionUtils {

  /** 사용자의 로그인 여부를 확인한다.  */
  public static boolean isLogin() {

    // 현재 인증된 사용자의 정보를 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      // 인증되지 않은 사용자 
      return false; 
    }else { 
      System.out.println(authentication.getPrincipal());
      // anonymousUser인 경우 String 타입 
      if(authentication.getPrincipal() instanceof String) {
        System.out.println(authentication.getPrincipal()); // anonymousUser
        return false; 
      }else if(authentication.getPrincipal() instanceof OidcUser) {
        OidcUser oidcUser = (OidcUser)authentication.getPrincipal();
        // oidcUser.getEmail();
        // oidcUser.getFullName();
        // oidcUser.getGivenName();
        // oidcUser.getFamilyName();
        // oidcUser.getPicture();
        // oidcUser.getAuthorities();
        // oidcUser.getAttributes();
        // oidcUser.getClaims();
        System.out.println("사용자 정보: " + oidcUser.getAttributes()); // {sub=google-oauth2|
          return true; 
      }else if(authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
        // oauth2User.getName();
        // oauth2User.getAttributes();
        // oauth2User.getAuthorities();
        System.out.println("사용자 정보: " + oauth2User.getAttributes()); // {sub=google-oauth2|
        System.out.println( oauth2User.getAttributes().get("email")); // 이메일
        return true; 
      }else { 
        // form login 
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        System.out.println("사용자 정보: " + userDetails.getUsername()); // aaa@gmail.com
        System.out.println("사용자 비밀번호: " + userDetails.getPassword()); // 비밀번호는 null로 나온다. 
        System.out.println("사용자 권한: " + userDetails.getAuthorities()); // [ROLE_ADMIN]
        String username = authentication.getName();
        System.out.println("현재 사용자: " + username); // aaa@gmail.com
        return true; 
      }
    }
  }//: 

}///~
