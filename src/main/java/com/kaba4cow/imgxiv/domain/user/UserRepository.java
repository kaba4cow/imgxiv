package com.kaba4cow.imgxiv.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
	Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
