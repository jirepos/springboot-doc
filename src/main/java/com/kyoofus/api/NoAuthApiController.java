package com.kyoofus.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kyoofus.api.dto.UserDto;

@RequestMapping("/api")
@Controller
public class NoAuthApiController {



  @GetMapping("/noauth")
  public ResponseEntity<UserDto> hello() {
    UserDto userDto = new UserDto();
    userDto.setUserId("test");
    userDto.setUserName("테스트");

    return ResponseEntity.ok(userDto); 
  }


  @GetMapping("/userinfo")
  public ResponseEntity<UserDto> userinfo() {
    UserDto userDto = new UserDto();
    userDto.setUserId("test");
    userDto.setUserName("테스트");

    return ResponseEntity.ok(userDto); 
  }


}





