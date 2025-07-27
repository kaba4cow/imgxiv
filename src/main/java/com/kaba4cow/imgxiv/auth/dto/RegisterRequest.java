package com.kaba4cow.imgxiv.auth.dto;

import com.kaba4cow.imgxiv.domain.user.validation.ValidEmail;
import com.kaba4cow.imgxiv.domain.user.validation.ValidUsername;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for registering a new user")
public class RegisterRequest {

	@NotBlank(message = "Username is required")
	@ValidUsername
	@Schema(//
			description = "Unique username", //
			example = "john_doe"//
	)
	private String username;

	@NotBlank(message = "Email is required")
	@ValidEmail
	@Schema(//
			description = "User email", //
			example = "john@example.com"//
	)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password is too short (min 8 characters)")
	@Size(max = 64, message = "Password is too long (max 64 characters)")
	@Schema(//
			description = "Password", //
			example = "password1234"//
	)
	private String password;

}
