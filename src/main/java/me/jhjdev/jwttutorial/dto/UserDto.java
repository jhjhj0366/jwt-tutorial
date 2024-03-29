package me.jhjdev.jwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @NotNull
  @Size(min = 3, max = 50)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NotNull
  @Size(min = 3, max = 50)
  private String nickname;
}
