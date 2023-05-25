# 보안 헤더 설정 


## X-Frame-Options 설정 

http 헤더에서 X-Frame-Options 속성을 가르키는데 frame 부분을 컨트롤 해준다.  디폴트 값은 deny이며 어떠한 사이트에도 frame 상에서 보여질수 없게 된다.
* SAMEORIGIN : 같은 도메인에서만 프레임을 사용
* DENY : 프레임을 사용할 수 없다
* ALLOW-FROM : 특정 도메인에서만 프레임을 사용할 수 있다.
    

```java 
http.headers(headers -> headers 
    .frameOptions(frameOptions -> frameOptions
        .sameOrigin() // X-Frame-Options: SAMEORIGIN
        // .disable() // X-Frame-Options: DENY
    )
);
```

sameOrigin()을 사용하는 경우 다음과 같이 응답 헤더에 X-Frame-Options: SAMEORIGIN이 설정된다. 

```properties
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: SAMEORIGIN
Content-Type: text/html;charset=UTF-8
```



## X-XSS-Protection 설정
HTTP X-XSS-Protection헤더는 Internet Explorer, Chrome 및 Safari에서 제공하는 기능으로서, (XSS) 공격을 감지 할 때 페이지 로드를 중지시킬 수 있다. 최신 브라우저에서는 Inline Javascript('unsafe-inline')사용을 못하게 하는 CSP(Content-Security-Policy) 보호기능이 있으나, 해당 기능을 지원하지 않는 구형 웹브라우저에서 사용자를 보호 할수 있는 기능을 제공할 수 있다.

브라우저들의 기존 버전에서는 X-XSS-Protection을 따로 설정해 주었으나, 현대 대부분의 브라우저들은 X-XSS-Protection이 자동으로 설정되어 브라우저 자체에서 필터링해준다.


X-XSS-Protection 헤더를 사용하여 XSS 공격을 방어합니다.
* X-XSS-Protection: 1  
  * XSS 필터를 활성화
  * 일반적으로 브라우저의 기본값
* X-XSS-Protection: 0 
  * XSS 필터를 비활성화
* X-XSS-Protection: 1; mode=block
  *  XSS 필터링을 사용합니다. 공격이 탐지되면 안전하지 않는 영역을 제거하는게 아니라, 페이지 렌더링을 중단

XSS 공격은 인풋 요소에 스크립트를 삽입하여 공격하는 방식이다. 이를 방지하기 위해 X-XSS-Protection 헤더를 사용하여 XSS 공격을 방어할 수 있다.


다음과 같이 xssProtection()을 사용한다. 
```java
http.headers(headers -> headers 
    .xssProtection(xss -> xss
        .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
    )
);
```    

응답헤더에 X-XSS-Protection: 1; mode=block이 설정된다.

```
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
```



## X-Content-Type-Options 설정
X-Content-Type-Options 헤더는 브라우저가 MIME 유형 스니핑을 수행하지 못하도록 하는 데 사용할 수 있으며 MIME 스니핑과 관련된 취약성을 방지하는 데 도움을 준다. nosniff 옵션을 사용하면 서버에서 내용이 text/html이라고하면 브라우저는 text/html로 렌더링한다. 

X-Content-Type-Options 헤더는 기본적으로 nosniff 로 설정된다. 

```
X-Content-Type-Options: nosniff
```

요청 대상이 style 유형이고 MIME 유형이 text/css가 아니거나 script 유형이고 MIME 유형이 JavaScript MIME 유형이 아닌 경우 요청을 차단한다. 



## Content-Security-Policy 설정 

* Mozilla가 개발한 표준으로 XSS, Data Injection, Click Jacking 등의 공격을 방지하기 위한 컨텐츠 기반 보안 정책
* SOP(Same Origin Policy)와 비슷하지만 CSP는 웹 사이트 관리자가 규칙 적용
* 웹 서버는 웹 사이트에서 사용 가능한 CSP 헤더를 브라우저에게 전달, 브라우저는 이를 기반으로 웹 페이지 렌더링
* 인라인 자바스크립트(태그 내에 자바스크립트 명령어 작성) 실행 금지 설정 가능
* 특정 웹사이트(origin)에서만 자원을 불러오도록 설정하여 공격자 서버에 요청을 차단
* 공격자가 웹 사이트에 본래 존재하지 않던 스크립트 삽입하는 것을 방지
* CSP 보안 정책을 정의한 상태에서 공격자가 다른 소스의 스크립트를 로딩 시 에러 출력


**CSP 사용헤더** 
* Content-Secuirty-Policy : W3C가 지정한 표준 헤더 - 주로 사용
* X-Content-Secuirty-Policy : FireFox/IE 구형 브라우저에서 사용되는 헤더
* X-WebKit-CSP : 크롬 기반 구형 브라우저에서 사용되는 헤더


**CSP 지시문 및 옵션** 
* 지시문
  * default-src : 모든 리소스에 대한 정책
  * script-src : Javascript 등 웹에서 실행 가능한 스크립트에 대한 정책
  * style-src : css에 대한 정책
  * connect-src : script src로 불러올 수 있는 url에 대한 정책
  * img-src : 이미지 ( data: URL에서 이미지가 로드되는 것을 허용하려면 data:를 지정 )
  * script-nonce : script-src에 nonce가 포함되는 정책
  * form-action : form 태그 내 action 부분에 대한 정책
  * object-src : 플러그인, 오브젝트에 대한 정책
  * media-src : video, audio
  * font-src : font
  * sandbox : HTML 샌드박스
  * reflected-xss : X-XSS-Protection header와 동일한 효과
  * report-uri : 정책 위반 케이스가 나타났을 때 내용을 전달할 URL
  * base-uri: <base> 요소에 나타날 수 있는 URL을 제한
* 옵션(src)
  * \* : 모든 것을 허용
  * 'none' : 모두 차단
  * 'self' : 현재 도메인만 허용
  * 'unsafe-inline' : 소스코드 내 인라인 자바스크립트 및 CSS 허용
  * 'unsafe-eval' : eval 같은 텍스트-자바스크립트 메커니즘 허용


### 예시 
**자체 리소스만 허용** 
웹 사이트 관리자는 모든 콘텐츠가 사이트 자체의 출처(하위 도메인 제외)에서 오기를 원한다. 
```
Content-Security-Policy: default-src 'self'
```

**신뢰할 수 있는 도메인 및 모든 하위 도메인 컨텐츠 허용**   
웹 사이트 관리자는 신뢰할 수 있는 도메인 및 모든 하위 도메인의 콘텐츠를 허용하려고 한다.(CSP가 설정된 도메인과 동일할 필요는 없음).
```
Content-Security-Policy: default-src 'self' example.com *.example.com
```

**오디오 또는 비디오 미디어는 신뢰할 수 있는 공급자로 제한** 

웹 사이트 관리자는 웹 애플리케이션 사용자가 자신의 콘텐츠에 모든 원본의 이미지를 포함할 수 있도록 허용하지만 오디오 또는 비디오 미디어는 신뢰할 수 있는 공급자로 제한하고 모든 스크립트는 신뢰할 수 있는 코드를 호스팅하는 특정 서버로만 제한하려고 한다.
```
Content-Security-Policy: default-src 'self'; img-src *; media-src example.org example.net; script-src userscripts.example.com
```

```java
http.headers(headers -> headers 
  .contentSecurityPolicy(csp -> csp
    .policyDirectives("script-src 'self' http://example.com; object-src https://example.com; ")
  )       
);
```        


다음과 같은 헤더가 반환된다.
```
Expires: 0
Content-Security-Policy: script-src 'self' http://example.com; object-src https://example.com; 
Content-Type: text/html;charset=UTF-8
Content-Language: en-US
```
## Strict-Transport-Security 설정 
기본적으로 Spring Security는 Strict Transport Security 헤더를 제공한다. 그러나 결과를 명시적으로 사용자 지정할 수 있다. 다음 예에서는 명시적으로 HSTS를 제공한다.

HSTS(HTTP Strict Transport Security)는, 간단히 기술하면, Web Site에 접속할 때, 강제적으로 HTTPS Protocol로만 접속하게 하는 기능이다. 즉  HTTPS Protocol을 지원하는 Web Site 에서, 자신은 HTTPS Protocol만 사용해서 통신할 수 있음을, 접속하고자 하는 Web Browser에게 알려 주는 기능이다. 보안을 강화시킬 목적으로, Web Browser에게 HTTPS Protocol만 사용하도록 강제하는 기능이다. 


```java
http
  // ...
  .headers(headers -> headers
    .httpStrictTransportSecurity(hsts -> hsts
      .includeSubDomains(true)
      .preload(true)
      .maxAgeInSeconds(31536000)
    )
  );
```      


```
Strict-Transport-Security: max-age=<expire-time>
Strict-Transport-Security: max-age=<expire-time>; includeSubDomains
Strict-Transport-Security: max-age=<expire-time>; includeSubDomains; preload
```

**max-age=\<expire-time\>**
HTTPS를 통해서만 사이트에 액세스할 수 있음을 브라우저가 기억해야 하는 시간(초)이다. 

**includeSubDomains** 
이 선택적 매개변수가 지정된 경우 이 규칙은 사이트의 모든 하위 도메인에도 적용된다. 

**preload** 
자세한 내용은 Strict Transport Security 사전 로드를 참조하십시오. 미리 로드를 사용하는 경우 max-age 지시문은 31536000(1년) 이상이어야 하며 includeSubDomains 지시문이 있어야 한다. 사양의 일부가 아니다. 






