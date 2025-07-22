package com.kaba4cow.imgxiv.domain.user.service;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultProfileService implements ProfileService {

	private final UserRepository userRepository;

	private final UserValidationService userValidationService;

	private final PasswordEncoder passwordEncoder;

	private final ProfileMapper profileMapper;

	@Override
	public ProfileDto getProfile(User user) {
		return profileMapper.mapToDto(user);
	}

	@Override
	public ProfileDto changeUsername(ChangeUsernameRequest request, User user) {
		String originalUsername = user.getCredentials().getUsername();
		String newUsername = request.getUsername();
		if (!Objects.equals(newUsername, originalUsername)) {
			userValidationService.ensureUsernameAvailable(newUsername);
			user.getCredentials().setUsername(newUsername);
			log.info("User {} changed username: {} -> {}", user.getId(), originalUsername, newUsername);
			return profileMapper.mapToDto(userRepository.save(user));
		} else
			return profileMapper.mapToDto(user);
	}

	@Override
	public ProfileDto changeEmail(ChangeEmailRequest request, User user) {
		String originalEmail = user.getCredentials().getEmail();
		String newEmail = request.getEmail();
		if (!Objects.equals(newEmail, originalEmail)) {
			userValidationService.ensureEmailAvailable(newEmail);
			user.getCredentials().setEmail(newEmail);
			log.info("User {} changed email: {} -> {}", user.getId(), originalEmail, newEmail);
			return profileMapper.mapToDto(userRepository.save(user));
		} else
			return profileMapper.mapToDto(user);
	}

	@Override
	public void changePassword(ChangePasswordRequest request, User user) {
		userValidationService.ensurePasswordsMatchOrThrow(request.getOldPassword(), user.getCredentials().getPasswordHash(),
				IllegalArgumentException::new);
		user.getCredentials().setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
		log.info("User {} changed password", user.getId());
		userRepository.save(user);
	}

}
