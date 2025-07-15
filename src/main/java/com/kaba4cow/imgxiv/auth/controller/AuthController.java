package com.kaba4cow.imgxiv.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.dto.UserDto;
import com.kaba4cow.imgxiv.auth.service.UserAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Authentication", //
		description = "User registration and login"//
)
@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final UserAuthService userAuthService;

	@Operation(//
			summary = "Register new user", //
			description = "Creates a new user and returns basic user info"//
	)
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.ok(userAuthService.register(request));
	}

	@Operation(//
			summary = "Authorizes existing user", //
			description = "Authorizes a user and returns token and basic user info"//
	)
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(userAuthService.login(request));
	}

}
