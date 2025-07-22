package com.kaba4cow.imgxiv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeEmailRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangePasswordRequest;
import com.kaba4cow.imgxiv.domain.user.dto.ChangeUsernameRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "User Profile", //
		description = """
				Endpoints for managing the authenticated user's profile.
				""")
@RequestMapping("/api/profile")
public interface ProfileControllerApiDoc {

	@Operation(//
			summary = "Get current authenticated user", //
			description = """
					Returns information about the currently authenticated user.
					""")
	@IsAuthenticated
	@GetMapping("/me")
	ResponseEntity<UserDto> getUserInfo(//

			@CurrentUser User user//

	);

	@Operation(//
			summary = "Change username", //
			description = """
					Allows an authenticated user to change their username. Returns the updated user info.
					""")
	@IsAuthenticated
	@PatchMapping("/username")
	ResponseEntity<UserDto> changeUsername(//

			@Valid //
			@RequestBody ChangeUsernameRequest request, //

			@CurrentUser User user//

	);

	@Operation(//
			summary = "Change email", //
			description = """
					Allows an authenticated user to change their email address. Returns the updated user info.
					""")
	@IsAuthenticated
	@PatchMapping("/email")
	ResponseEntity<UserDto> changeEmail(//

			@Valid //
			@RequestBody ChangeEmailRequest request, //

			@CurrentUser User user//

	);

	@Operation(//
			summary = "Change password", //
			description = """
					Allows an authenticated user to change their password. No content is returned on success.
					""")
	@IsAuthenticated
	@PatchMapping("/password")
	ResponseEntity<Void> changePassword(//

			@Valid //
			@RequestBody ChangePasswordRequest request, //

			@CurrentUser User user//
	);

}
