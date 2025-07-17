package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.context.CurrentUserService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/profile")
@RestController
public class ProfileController {

	private final CurrentUserService currentUserService;

	private final ProfileService profileService;

	@PatchMapping("/username")
	public ResponseEntity<UserDto> changeUsername(@RequestBody @Valid ChangeUsernameRequest request) {
		User user = currentUserService.getUserOrThrow();
		UserDto result = profileService.changeUsername(request, user);
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/email")
	public ResponseEntity<UserDto> changeEmail(@RequestBody @Valid ChangeEmailRequest request) {
		User user = currentUserService.getUserOrThrow();
		UserDto result = profileService.changeEmail(request, user);
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
		User user = currentUserService.getUserOrThrow();
		profileService.changePassword(request, user);
		return ResponseEntity.noContent().build();
	}

}
