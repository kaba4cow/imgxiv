package com.kaba4cow.imgxiv.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.AuthRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
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

	private final UserMapper userMapper;

	@Override
	public UserDto register(RegisterRequest request) {
		userValidationService.ensureUsernameAvailable(request.getUsername());
		userValidationService.ensureEmailAvailable(request.getEmail());
		User user = new User();
		user.getCredentials().setUsername(request.getUsername());
		user.getCredentials().setEmail(request.getEmail());
		user.getCredentials().setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRole.USER);
		User saved = userRepository.save(user);
		log.info("Registered new user: {}", saved);
		return userMapper.mapToDto(saved);
	}

	@Override
	public AuthDto authenticate(AuthRequest request) {
		User user = userRepository.findByUsernameOrEmailOrThrow(request.getUsernameOrEmail());
		userValidationService.ensurePasswordsMatch(request.getPassword(), user.getCredentials().getPasswordHash());
		String token = jwtService.generateToken(user);
		return new AuthDto(token, userMapper.mapToDto(user));
	}

}
