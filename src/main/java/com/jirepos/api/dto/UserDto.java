package com.jirepos.api.dto;

// import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
// @Schema(name = "UserDto", description = "사용자 정보")
/** 사용자 정보를 담을 DTO  */
public class UserDto {
  /** 사용자 아이디 */
  // @Schema(description = "사용자 아이디", example = "test", required = true)
  private String userId; 
  /** 사용자 이름  */
  // @Schema(description = "사용자 이름", example = "테스트", required = true)
  private String userName; 
}
