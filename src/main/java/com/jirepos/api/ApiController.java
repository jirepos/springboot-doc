package com.jirepos.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jirepos.api.dto.UserDto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// @RestController
// @RequestMapping("/api")
public class ApiController {

  
  // @GetMapping("/user")
  // @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  // public ResponseEntity<UserDto> user() {
  //   UserDto dto = new UserDto();
  //   dto.setUserId("123");
  //   dto.setUserName("John Doe");
  //   return ResponseEntity.ok(dto);
  // }//:


  // @GetMapping("/login")
  // @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  // public ResponseEntity<UserDto> login(
  //   @Parameter(description = "사용자 아이디", example = "test", required = true) @RequestParam("userId") String userId ) {
  //   UserDto dto = new UserDto();
  //   dto.setUserId("123");
  //   dto.setUserName("John Doe");
  //   return ResponseEntity.ok(dto);
  // }//:


  // @GetMapping("/get-user/{userId}/{userName}")
  // @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
  // public ResponseEntity<UserDto> getUser(
  //   @Parameter(description = "사용자 아이디", example = "test", required = true)  @PathVariable("userId") String userId,
  //   @Parameter(description = "사용자 이름", example = "test", required = true) @PathVariable("userName") String userName
  // ) {
  //   UserDto dto = new UserDto();
  //   dto.setUserId(userId);
  //   dto.setUserName(userName); 
  //   return ResponseEntity.ok(dto);
  // }//:


  @PostMapping("/add-user")
  @Tag(name = "user")
  @Operation(summary = "사용자 추가", description = "사용자 정보를 추가합니다.")
  public ResponseEntity<UserDto> addUser(
    @Parameter(description = "추가할 사용자 객체",  required = true)  @RequestBody UserDto userDto
  ) {
    
    return ResponseEntity.ok(userDto);
  }//:

  @PostMapping("/update-user")
  @Operation(summary = "사용자 수정", description = "사용자 정보를 추가합니다.")
  @Tag(name = "admin")
  public ResponseEntity<UserDto> updateUser(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 사용자 객체")   @RequestBody UserDto userDto
  ) {
    
    return ResponseEntity.ok(userDto);
  }//:


}

