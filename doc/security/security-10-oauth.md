# OAuth 


* oAuth2Login은 OAuth2를 활용한 로그인 Flow 자체를 제공하는 기능이다.
* oAuth2Client는 클라이언트에서 수행할 역할들을 단위 단위 별로 쪼개서 원하는 대로 구현할 수 있게 기능을 제공한다고 보면 된다.

예를 들어서 Google, Naver등등 외부의 인증 서버와 연동을 해서 로그인을 구현하고 싶을 때, Spring서버가 Client가 되어야하는데, 이 때 oAuth2Client()를 사용하면 된다.


```java
http.oauth2Login(Customizer.withDefaults());
        return http.build();
```


```java
http.oauth2Client(Customizer.withDefaults());
```



## 참조 
[Spring security와 OAuth2. 개념 정리](https://velog.io/@juhyeon1114/Spring-security%EC%99%80-OAuth2.-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC) 


