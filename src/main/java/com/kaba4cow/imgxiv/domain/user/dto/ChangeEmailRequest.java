package com.kaba4cow.imgxiv.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ChangeEmailRequest {

	@NotBlank(message = "Email is required")
	@Size(max = 64, message = "Email is too long (max 64 characters)")
	@Email(message = "Email must be valid")
	@Schema(//
			description = "User email", //
			example = "john@example.com"//
	)
	private String email;

}
