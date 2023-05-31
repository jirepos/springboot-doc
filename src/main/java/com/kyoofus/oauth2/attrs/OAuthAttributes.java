package com.kyoofus.oauth2.attrs;


import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다. 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAttributes {

    /** 사용자 attributes */
    private Map<String, Object> attributes;
    /** 사용자 식별자 attribute 속성 이름 */
    private String nameAttributeKey;
    /** 사용자의 이름  */
    private String name;
    /** 사용자의 이메일 */
    private String email;
    /** 사용자 사진 URL */
    private String picture;


    /**
     * Provider를 구분하여 OAuthAttributes를 생성한다.
     * @param registrationId  application.yml에 설정한 registraion id. 예) google, github 
     * @param userNameAttributeName 사용자 식별자의 속성 이름 
     * @param attributes 사용자 attributes
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // applicaiton.yml의  spring.security.oauth2.client.registration.google이 registrationID가 된다. 
        if (registrationId.equals("azure")) {
            return ofAzure(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    /** Provider가 google 인 경우 OAuthAttributes를 생성한다.  */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture"));
    }
    /** Provider가 Microsoft Azure 인 경우 OAuthAttributes를 생성한다.  */
    private static OAuthAttributes ofAzure(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture"));
    }    

   
}