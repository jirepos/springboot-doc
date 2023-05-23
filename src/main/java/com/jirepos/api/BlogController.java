package com.jirepos.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jirepos.api.dto.UserDto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// @OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "My Blog API", description = "My Blog", version = "v1"), tags = {
//     @io.swagger.v3.oas.annotations.tags.Tag(name = "blog", description = "사용자 관련 API")
// }, servers = {
//     @io.swagger.v3.oas.annotations.servers.Server(description = "Local Server", url = "http://localhost"),
//     @io.swagger.v3.oas.annotations.servers.Server(description = "Dev Server", url = "http://dev.yoursite.com")
// })
// @RestController
// @RequestMapping("/blog")
public class BlogController {

  @GetMapping("/user")
  @Tag(name = "blog")
  @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  public ResponseEntity<UserDto> user() {
    UserDto dto = new UserDto();
    dto.setUserId("123");
    dto.setUserName("John Doe");
    return ResponseEntity.ok(dto);
  }// :

}
