package com.kyoofus.api;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;


// 클래스 수준에서 사용해야 합니다. 
@OpenAPIDefinition(
  info = @io.swagger.v3.oas.annotations.info.Info(
    title = "YoWiki Open API Explorer",   // Api 문서 제목
    description = "YoWiki에서 제공하는 Open API에 대한 설명서입니다. 또한 API를 테스트할 수 있습니다.",  // API 문서에 대한 자세한 설명 
    version = "v1"  // API 버전 
  ),
  // Tag를 사용하여 api들을 그룹화 할 수 있습니다.
  tags = {
    @io.swagger.v3.oas.annotations.tags.Tag(name = "login", description = "Login 관련 API"),
    @io.swagger.v3.oas.annotations.tags.Tag(name = "wiki", description = "Wiki 관련 API"),
    @io.swagger.v3.oas.annotations.tags.Tag(name = "admin", description = "관리자 관련 API")
    // Controller 클래스에서도 @Tag 어노테이션을 사용하여 그룹화가 가능합니다. 
  },
  // Server를 사용하여 API를 테스트할 수 있는 URL을 정의할 수 있습니다. 
  // local 환경의 localhost를 사용하여 테스트하도록 만들려면 http://localhost를 사용합니다. 
  servers = {
    @io.swagger.v3.oas.annotations.servers.Server(
      description = "Local Server",
      url = "http://localhost"
    ),
    // 개발환경의 url을 설정합니다. 
    @io.swagger.v3.oas.annotations.servers.Server(
      description = "Develop Server",
      url = "http://dev.yourdomain.com"
    ),
    // 운영환경의 url을 설정합니다. 
    @io.swagger.v3.oas.annotations.servers.Server(
      description = "Production Server",
      url = "http://prod.yourdomain.com"
    )
  }
)
/**
 * OpenAPI 설정 클래스입니다.  API 문서에 대한 제목, 설명, 버전, 그리고 테스트할 수 있는 URL을 설정합니다. 
 */
// @Configuration 어노테이션을 사용하여 멤버가 없는 자바 구성 클래스를 선언합니다.
@Configuration
public class OpenApiConfig {
}/// 
