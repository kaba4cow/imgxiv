package com.kaba4cow.imgxiv.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for authorizing an existing user")
public class AuthRequest {

	@NotBlank(message = "Username or email is required")
	@Schema(//
			description = "Username or email", //
			example = "john_doe"//
	)
	private String usernameOrEmail;

	@NotBlank(message = "Password is required")
	@Schema(//
			description = "Password", //
			example = "password1234"//
	)
	private String password;

}
