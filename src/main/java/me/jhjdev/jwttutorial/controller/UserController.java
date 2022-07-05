package me.jhjdev.jwttutorial.controller;

import javax.validation.Valid;
import me.jhjdev.jwttutorial.dto.UserDto;
import me.jhjdev.jwttutorial.entity.Users;
import me.jhjdev.jwttutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<Users> signup(@Valid @RequestBody UserDto userDto) {
    return ResponseEntity.ok(userService.signUp(userDto));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<Users> getMyUserInfo() {
    return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
  }

  public ResponseEntity<Users> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
  }
}
