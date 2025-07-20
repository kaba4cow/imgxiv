package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.common.controller.CurrentUserAwareController;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;
import com.kaba4cow.imgxiv.domain.user.dto.UserMapper;
import com.kaba4cow.imgxiv.domain.user.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "User Profile", //
		description = "Endpoints for managing the authenticated user's profile"//
)
@RequestMapping("/api/profile")
@RestController
public class ProfileController extends CurrentUserAwareController {

	private final ProfileService profileService;

	private final UserMapper userMapper;

	@Operation(//
			summary = "Get current authenticated user", //
			description = "Returns information about the currently authenticated user"//
	)
	@IsAuthenticated
	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(@ModelAttribute User currentUser) {
		return ResponseEntity.ok(userMapper.mapToDto(currentUser));
	}

	@Operation(//
			summary = "Change username", //
			description = "Allows an authenticated user to change their username. Returns the updated user info"//
	)
	@IsAuthenticated
	@PatchMapping("/username")
	public ResponseEntity<UserDto> changeUsername(@Valid @RequestBody ChangeUsernameRequest request,
			@ModelAttribute User currentUser) {
		UserDto result = profileService.changeUsername(request, currentUser);
		return ResponseEntity.ok(result);
	}

	@Operation(//
			summary = "Change email", //
			description = "Allows an authenticated user to change their email address. Returns the updated user info"//
	)
	@IsAuthenticated
	@PatchMapping("/email")
	public ResponseEntity<UserDto> changeEmail(@Valid @RequestBody ChangeEmailRequest request,
			@ModelAttribute User currentUser) {
		UserDto result = profileService.changeEmail(request, currentUser);
		return ResponseEntity.ok(result);
	}

	@Operation(//
			summary = "Change password", //
			description = "Allows an authenticated user to change their password. No content is returned on success"//
	)
	@IsAuthenticated
	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request,
			@ModelAttribute User currentUser) {
		profileService.changePassword(request, currentUser);
		return ResponseEntity.noContent().build();
	}

}
