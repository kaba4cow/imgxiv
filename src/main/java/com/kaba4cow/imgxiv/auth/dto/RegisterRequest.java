package com.kaba4cow.imgxiv.auth.dto;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest {

	@NotBlank(message = "Username is required")
	@Size(min = 2, message = "Username is too short (min 4 characters)")
	@Size(max = 32, message = "Username is too long (max 50 characters)")
	private String username;

	@NotBlank(message = "Email is required")
	@Size(max = 64, message = "Email is too long (max 64 characters)")
	@Email(message = "Email must be valid")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password is too short (min 8 characters)")
	@Size(max = 64, message = "Password is too long (max 64 characters)")
	private String password;

}
