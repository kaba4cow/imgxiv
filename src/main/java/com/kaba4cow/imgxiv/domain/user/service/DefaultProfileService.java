package com.kaba4cow.imgxiv.domain.user.service;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
import com.kaba4cow.imgxiv.domain.user.validation.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultProfileService implements ProfileService {

	private final UserRepository userRepository;

	private final UserValidationService userValidationService;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	@Override
	public UserDto changeUsername(ChangeUsernameRequest request, User user) {
		String newUsername = request.getUsername();
		if (!Objects.equals(newUsername, user.getUsername())) {
			userValidationService.ensureUsernameAvailable(newUsername);
			user.setUsername(newUsername);
			log.info("User {} changed username: {} -> {}", user.getId(), user.getUsername(), newUsername);
			return userMapper.mapToDto(userRepository.save(user));
		} else
			return userMapper.mapToDto(user);
	}

	@Override
	public UserDto changeEmail(ChangeEmailRequest request, User user) {
		String newEmail = request.getEmail();
		if (!Objects.equals(newEmail, user.getEmail())) {
			userValidationService.ensureEmailAvailable(newEmail);
			user.setEmail(newEmail);
			log.info("User {} changed email: {} -> {}", user.getId(), user.getEmail(), newEmail);
			return userMapper.mapToDto(userRepository.save(user));
		} else
			return userMapper.mapToDto(user);
	}

	@Override
	public void changePassword(ChangePasswordRequest request, User user) {
		userValidationService.ensurePasswordsMatch(request.getOldPassword(), user.getPasswordHash());
		user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
		log.info("User {} changed password", user.getId());
	}

}
