package com.kaba4cow.imgxiv.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for authorizing an existing user")
public class LoginRequest {

	@Schema(//
			description = "Username or email", //
			example = "john_doe"//
	)
	private String usernameOrEmail;

	@Schema(//
			description = "Password", //
			example = "password1234"//
	)
	private String password;

}
