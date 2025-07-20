package com.kaba4cow.imgxiv.common.validation;

import java.util.function.Function;

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
		ensureUsernameAvailableOrThrow(username, UsernameConflictException::new);
	}

	public <T extends Throwable> void ensureUsernameAvailableOrThrow(String username, Function<String, T> exceptionSupplier)
			throws T {
		if (userRepository.existsByUsername(username))
			throw exceptionSupplier.apply("Username already taken");
	}

	public void ensureEmailAvailable(String email) throws EmailConflictException {
		ensureEmailAvailableOrThrow(email, EmailConflictException::new);
	}

	public <T extends Throwable> void ensureEmailAvailableOrThrow(String email, Function<String, T> exceptionSupplier)
			throws T {
		if (userRepository.existsByEmail(email))
			throw exceptionSupplier.apply("Email already taken");
	}

	public void ensurePasswordsMatch(String password, String passwordHash) throws PasswordMismatchException {
		ensurePasswordsMatchOrThrow(password, passwordHash, PasswordMismatchException::new);
	}

	public <T extends Throwable> void ensurePasswordsMatchOrThrow(String password, String passwordHash,
			Function<String, T> exceptionSupplier) throws T {
		if (!passwordEncoder.matches(password, passwordHash))
			throw exceptionSupplier.apply("Passwords do not match");
	}

}
