package com.kaba4cow.imgxiv.common.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.EmailConflictException;
import com.kaba4cow.imgxiv.common.exception.PasswordMismatchException;
import com.kaba4cow.imgxiv.common.exception.UsernameConflictException;
import com.kaba4cow.imgxiv.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserValidationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public void ensureUsernameAvailable(String username) throws UsernameConflictException {
		if (userRepository.existsByUsername(username))
			throw new UsernameConflictException("Username already taken");
	}

	public void ensureEmailAvailable(String email) throws EmailConflictException {
		if (userRepository.existsByEmail(email))
			throw new EmailConflictException("Email already taken");
	}

	public void ensurePasswordsMatch(String password, String passwordHash) throws PasswordMismatchException {
		if (!passwordEncoder.matches(password, passwordHash))
			throw new PasswordMismatchException("Passwords do not match");
	}

}
