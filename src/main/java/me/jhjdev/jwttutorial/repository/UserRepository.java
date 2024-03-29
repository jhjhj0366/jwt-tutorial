package me.jhjdev.jwttutorial.repository;

import me.jhjdev.jwttutorial.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
  @EntityGraph(attributePaths = "authorities")
  Optional<Users> findOneWithAuthoritiesByUsername(String username);
}