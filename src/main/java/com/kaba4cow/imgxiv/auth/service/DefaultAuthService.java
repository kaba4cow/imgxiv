package com.kaba4cow.imgxiv.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.AuthRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileMapper;
import com.kaba4cow.imgxiv.domain.user.model.Credentials;
import com.kaba4cow.imgxiv.domain.user.model.User;
import com.kaba4cow.imgxiv.domain.user.model.UserRole;
import com.kaba4cow.imgxiv.domain.user.service.UserValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final UserValidationService userValidationService;

	private final ProfileMapper profileMapper;

	@Override
	public ProfileDto register(RegisterRequest request) {
		userValidationService.ensureUsernameAvailable(request.getUsername());
		userValidationService.ensureEmailAvailable(request.getEmail());
		User saved = userRepository.save(User.builder()//
				.role(UserRole.defaultRole())//
				.credentials(Credentials.builder()//
						.username(request.getUsername())//
						.email(request.getEmail())//
						.passwordHash(passwordEncoder.encode(request.getPassword()))//
						.build())//
				.build());
		log.info("Registered new user: {}", saved);
		return profileMapper.mapToDto(saved);
	}

	@Override
	public AuthDto authenticate(AuthRequest request) {
		User user = userRepository.findByUsernameOrEmailOrThrow(request.getUsernameOrEmail());
		userValidationService.ensurePasswordsMatch(request.getPassword(), user.getCredentials().getPasswordHash());
		String token = jwtService.generateToken(user);
		return new AuthDto(token, profileMapper.mapToDto(user));
	}

}
