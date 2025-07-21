package com.kaba4cow.imgxiv.setup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminUserInitializer implements ApplicationRunner {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Value("${admin.username}")
	private String username;

	@Value("${admin.email}")
	private String email;

	@Value("${admin.password}")
	private String password;

	@Override
	public void run(ApplicationArguments args) {
		if (alreadyExists())
			return;
		User saved = userRepository.save(createAdmin());
		log.info("Registered admin {}", saved);
	}

	private boolean alreadyExists() {
		return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
	}

	private User createAdmin() {
		User admin = new User();
		admin.getCredentials().setUsername(username);
		admin.getCredentials().setEmail(email);
		admin.getCredentials().setPasswordHash(passwordEncoder.encode(password));
		admin.setRole(UserRole.ADMIN);
		return admin;
	}

}
