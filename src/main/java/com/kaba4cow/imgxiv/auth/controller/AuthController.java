package com.kaba4cow.imgxiv.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.dto.AuthResponse;
import com.kaba4cow.imgxiv.auth.dto.LoginRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.service.UserAuthService;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthControllerApiDoc {

	private final UserAuthService userAuthService;

	@Override
	public ResponseEntity<UserDto> registerUser(RegisterRequest request) {
		return ResponseEntity.ok(userAuthService.register(request));
	}

	@Override
	public ResponseEntity<AuthResponse> authenticateUser(LoginRequest request) {
		return ResponseEntity.ok(userAuthService.login(request));
	}

}
