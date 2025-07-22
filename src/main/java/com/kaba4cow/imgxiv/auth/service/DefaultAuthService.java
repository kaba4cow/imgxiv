package com.kaba4cow.imgxiv.auth.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.registrar.UserRegistrar;
import com.kaba4cow.imgxiv.common.validation.UserValidationService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

	private final UserRepository userRepository;

	private final UserRegistrar userRegistrar;

	private final JwtService jwtService;

	private final UserValidationService userValidationService;

	private final UserMapper userMapper;

	@Override
	public UserDto register(RegisterRequest request) {
		userValidationService.ensureUsernameAvailable(request.getUsername());
		userValidationService.ensureEmailAvailable(request.getEmail());
		User user = userRegistrar.registerUser(request);
		return userMapper.mapToDto(user);
	}

	@Override
	public AuthDto login(LoginRequest request) {
		User user = userRepository.findByUsernameOrEmailOrThrow(request.getUsernameOrEmail());
		userValidationService.ensurePasswordsMatch(request.getPassword(), user.getCredentials().getPasswordHash());
		String token = jwtService.generateToken(user);
		return new AuthDto(token, userMapper.mapToDto(user));
	}

}
