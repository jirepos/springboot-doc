package com.kyoofus.security.basicauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kyoofus.api.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;

/** Basic Authentication 테스트를 위한 컨트롤러이다. */
@RestController
@RequestMapping("/app")
public class CustomBasicAuthController {



  /** 간단한 사용방법을 예시하기 위한 메소드이다. */
  @GetMapping("/basic/user")
  // @Tag(name ="login")
  // @Operation 어노테이션을 사용하여 API에 대한 설명을 붙인다.
  @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  public ResponseEntity<UserDto> getUser() {
    UserDto dto = new UserDto();
    dto.setUserId("123");
    dto.setUserName("John Doe");
    return ResponseEntity.ok(dto);
  }// :


  /** 간단한 사용방법을 예시하기 위한 메소드이다. */
  @GetMapping("/public/user")
  // @Tag(name ="login")
  // @Operation 어노테이션을 사용하여 API에 대한 설명을 붙인다.
  @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  public ResponseEntity<UserDto> getGuest() {
    UserDto dto = new UserDto();
    dto.setUserId("123");
    dto.setUserName("John Doe");
    return ResponseEntity.ok(dto);
  }// :

  

}
