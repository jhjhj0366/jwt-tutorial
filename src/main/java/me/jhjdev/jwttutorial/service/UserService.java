package me.jhjdev.jwttutorial.service;

import java.util.Collections;
import java.util.Optional;
import me.jhjdev.jwttutorial.dto.UserDto;
import me.jhjdev.jwttutorial.entity.Authority;
import me.jhjdev.jwttutorial.entity.Users;
import me.jhjdev.jwttutorial.repository.UserRepository;
import me.jhjdev.jwttutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public Users signUp(UserDto userDto) {
    if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null)
        != null) {
      throw new RuntimeException("이미 가입되어 있는 유저입니다.");
    }

    Authority authority = Authority.builder().authorityName("ROLE_USER").build();

    Users users =
        Users.builder()
            .username(userDto.getUsername())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .nickname(userDto.getNickname())
            .authorities(Collections.singleton(authority))
            .activated(true)
            .build();

    return userRepository.save(users);
  }

  @Transactional(readOnly = true)
  public Optional<Users> getUserWithAuthorities(String username) {
    return userRepository.findOneWithAuthoritiesByUsername(username);
  }

  @Transactional(readOnly = true)
  public Optional<Users> getMyUserWithAuthorities() {
    return SecurityUtil.getCurrentUsername()
        .flatMap(userRepository::findOneWithAuthoritiesByUsername);
  }
}
