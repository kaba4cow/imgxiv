package com.kaba4cow.imgxiv.domain.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsernameOrEmail(String usernameOrEmail);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
