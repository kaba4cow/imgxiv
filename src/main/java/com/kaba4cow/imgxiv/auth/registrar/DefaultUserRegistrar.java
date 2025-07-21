package com.kaba4cow.imgxiv.auth.registrar;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultUserRegistrar implements UserRegistrar {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(RegisterRequest request) {
		User user = new User();
		user.getCredentials().setUsername(request.getUsername());
		user.getCredentials().setEmail(request.getEmail());
		user.getCredentials().setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRole.USER);
		User saved = userRepository.save(user);
		log.info("Registered new user: {}", saved);
		return saved;
	}

}
