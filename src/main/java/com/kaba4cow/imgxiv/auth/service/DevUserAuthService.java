package com.kaba4cow.imgxiv.auth.service;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.dto.UserDto;
import com.kaba4cow.imgxiv.auth.dto.UserMapper;
import com.kaba4cow.imgxiv.auth.jwt.JwtService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Profile("dev")
@Service
public class DevUserAuthService implements UserAuthService {

	private final UserRepository userRepository;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	@Override
	public UserDto register(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername()))
			throw new IllegalArgumentException("Username already taken");
		if (userRepository.existsByEmail(request.getEmail()))
			throw new IllegalArgumentException("Email already taken");
		User user = registerUser(request);
		return userMapper.mapToDto(user);
	}

	private User registerUser(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		User user = findByUsernameOrEmail(request.getUsernameOrEmail())//
				.orElseThrow(() -> new UsernameNotFoundException("Not found"));
		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
			throw new BadCredentialsException("Invalid credentials");
		String token = jwtService.generateToken(user);
		return new AuthResponse(token, userMapper.mapToDto(user));
	}

	@Override
	public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.findByUsernameOrEmail(usernameOrEmail);
	}

}
