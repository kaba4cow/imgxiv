package com.kaba4cow.imgxiv.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.dto.AuthDto;
import com.kaba4cow.imgxiv.auth.dto.AuthRequest;
import com.kaba4cow.imgxiv.auth.dto.RegisterRequest;
import com.kaba4cow.imgxiv.auth.service.AuthService;
import com.kaba4cow.imgxiv.domain.user.dto.ProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

	private final AuthService authService;

	@Override
	public ResponseEntity<ProfileDto> registerUser(RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@Override
	public ResponseEntity<AuthDto> authenticateUser(AuthRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

}
