package com.kaba4cow.imgxiv.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;
import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
import com.kaba4cow.imgxiv.domain.user.validation.UserValidationService;
import com.kaba4cow.imgxiv.util.PersistLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUserAuthService implements UserAuthService {

	private final UserRepository userRepository;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final UserValidationService userValidationService;

	private final UserMapper userMapper;

	@Override
	public UserDto register(RegisterRequest request) {
		userValidationService.ensureUsernameAvailable(request.getUsername());
		userValidationService.ensureEmailAvailable(request.getEmail());
		User user = registerUser(request);
		return userMapper.mapToDto(user);
	}

	private User registerUser(RegisterRequest request) {
		User user = new User();
		user.getCredentials().setUsername(request.getUsername());
		user.getCredentials().setEmail(request.getEmail());
		user.getCredentials().setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRole.USER);
		return PersistLog.log(userRepository.save(user));
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		User user = findByUsernameOrEmail(request.getUsernameOrEmail())//
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		userValidationService.ensurePasswordsMatch(request.getPassword(), user.getCredentials().getPasswordHash());
		String token = jwtService.generateToken(user);
		return new AuthResponse(token, userMapper.mapToDto(user));
	}

	@Override
	public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.findByUsernameOrEmail(usernameOrEmail);
	}

}
