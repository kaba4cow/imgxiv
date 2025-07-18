package com.kaba4cow.imgxiv.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.service.UserAuthService;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Authentication", //
		description = "User registration and login endpoints"//
)
@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final UserAuthService userAuthService;

	@Operation(//
			summary = "Register new user", //
			description = "Creates a new user account and returns basic user information"//
	)
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.ok(userAuthService.register(request));
	}

	@Operation(//
			summary = "Authenticate user and return token", //
			description = "Authenticates the provided credentials and returns a JWT token along with user info"//
	)
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(userAuthService.login(request));
	}

}
