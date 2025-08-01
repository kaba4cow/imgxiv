package com.kaba4cow.imgxiv.domain.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {

	Page<User> findAllByRole(UserRole role, Pageable pageable);

	default User findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("User", id));
	}

	default User findByUsernameOrEmailOrThrow(String usernameOrEmail) {
		return findByUsernameOrEmail(usernameOrEmail).orElseThrow(() -> new NotFoundException("User", usernameOrEmail));
	}

	@Query("SELECT u FROM User u WHERE u.credentials.username = :username")
	Optional<User> findByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.credentials.username = :usernameOrEmail OR u.credentials.email = :usernameOrEmail")
	Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.credentials.username = :username")
	boolean existsByUsername(@Param("username") String username);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.credentials.email = :email")
	boolean existsByEmail(@Param("email") String email);

}
