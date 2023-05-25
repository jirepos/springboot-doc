package com.jirepos.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jirepos.api.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


/** API 명세화 설명을 위한 Login Controller 클래스이다.  */
@RestController
@RequestMapping("/api/v1")
// Tag를 사용하여 LoginController의 API들을 login으로 묶어준다.
@Tag(name ="login")
public class AuthApiController {


  /** 간단한 사용방법을 예시하기 위한 메소드이다.  */
  @GetMapping("/get-user")
  // @Tag(name ="login")
  // @Operation 어노테이션을 사용하여 API에 대한 설명을 붙인다. 
  @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  public ResponseEntity<UserDto> getUser() {
    UserDto dto = new UserDto();
    dto.setUserId("123");
    dto.setUserName("John Doe");
    return ResponseEntity.ok(dto);
  }// :


  /** URL parameter 명세화 설명을 위한 메소드이다.  */
  @GetMapping("/login")
  @Operation(summary = "로그인", description = "사용자 로그인 처리를 한다.")
  public ResponseEntity<UserDto> login(
    // @Parameter를 사용한다. 
    @Parameter(description = "사용자 아이디", example = "test", required = true) @RequestParam("userId") String userId ) {
    UserDto dto = new UserDto();
    dto.setUserId("123");
    dto.setUserName("John Doe");
    return ResponseEntity.ok(dto);
  }//:

  /** Path Variables를 사용하는 API 명세화 설명을 위한 메소드이다.  */
  @GetMapping("/get-user/{userId}/{userName}")
  @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  public ResponseEntity<UserDto> getUser(
    @Parameter(description = "사용자 아이디", example = "test", required = true)  @PathVariable("userId") String userId,
    @Parameter(description = "사용자 이름", example = "test", required = true) @PathVariable("userName") String userName
  ) {
    UserDto dto = new UserDto();
    dto.setUserId(userId);
    dto.setUserName(userName); 
    return ResponseEntity.ok(dto);
  }//:


  /** @RequestBody를 사용하는 API 명세화 설명을 위한 메소드이다.  */
  @PostMapping("/add-user")
  // @Tag(name = "user")
  @Operation(summary = "사용자 추가", description = "사용자 정보를 추가합니다.")
  public ResponseEntity<UserDto> addUser(
    @Parameter(description = "추가할 사용자 객체",  required = true)  @RequestBody UserDto userDto
  ) {
    
    return ResponseEntity.ok(userDto);
  }//:

}