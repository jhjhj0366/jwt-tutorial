package me.jhjdev.jwttutorial.service;

import java.util.List;
import java.util.stream.Collectors;
import me.jhjdev.jwttutorial.entity.Users;
import me.jhjdev.jwttutorial.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String username) {
    return userRepository.findOneWithAuthoritiesByUsername(username)
            .map(users -> createUser(username, users))
            .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
  }

  private org.springframework.security.core.userdetails.User createUser(String username, Users users) {
    if (!users.isActivated()) {
      throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
    }
    List<GrantedAuthority> grantedAuthorities = users.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
            .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(users.getUsername(),
            users.getPassword(),
            grantedAuthorities);
  }
}