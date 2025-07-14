package com.kaba4cow.imgxiv.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.dto.UserDto;
import com.kaba4cow.imgxiv.auth.dto.UserMapper;
import com.kaba4cow.imgxiv.auth.jwt.JwtService;
import com.kaba4cow.imgxiv.auth.service.DevUserService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class DevUserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtService jwtService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private DevUserService userService;

	@Test
	void throwIfUsernameTaken() {
		RegisterRequest request = new RegisterRequest("user", "mail@mail.com", "pass");

		when(userRepository.existsByUsername("user")).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> userService.register(request));
	}

	@Test
	void throwIfEmailTaken() {
		RegisterRequest request = new RegisterRequest("user", "mail@mail.com", "pass");

		when(userRepository.existsByUsername("user")).thenReturn(false);
		when(userRepository.existsByEmail("mail@mail.com")).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> userService.register(request));
	}

	@Test
	void registerNewUser() {
		RegisterRequest request = new RegisterRequest("user", "mail@mail.com", "pass");

		when(userRepository.existsByUsername("user")).thenReturn(false);
		when(userRepository.existsByEmail("mail@mail.com")).thenReturn(false);
		when(passwordEncoder.encode("pass")).thenReturn("hashed");
		when(userRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

		when(userMapper.mapToDto(Mockito.any())).thenReturn(new UserDto("user", "mail@mail.com"));

		UserDto result = userService.register(request);

		assertEquals("user", result.getUsername());
		assertEquals("mail@mail.com", result.getEmail());
	}

	@Test
	void registerAndLogin() {
		User user = new User();
		user.setUsername("user");
		user.setEmail("mail@mail.com");
		user.setPasswordHash("hashed");

		when(userRepository.findByUsernameOrEmail("user")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("pass", "hashed")).thenReturn(true);
		when(jwtService.generateToken(user)).thenReturn("mock-token");
		when(userMapper.mapToDto(user)).thenReturn(new UserDto("user", "mail@mail.com"));

		AuthResponse response = userService.login(new LoginRequest("user", "pass"));

		assertEquals("mock-token", response.getToken());
		assertEquals("user", response.getUser().getUsername());
	}

}
