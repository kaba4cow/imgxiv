package com.kaba4cow.imgxiv.domain.user.dto;

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
public class ChangePasswordRequest {

	@NotBlank(message = "Old password is required")
	@Schema(//
			description = "Old password", //
			example = "password1234"//
	)
	private String oldPassword;

	@NotBlank(message = "New password is required")
	@Size(min = 8, message = "Password is too short (min 8 characters)")
	@Size(max = 64, message = "Password is too long (max 64 characters)")
	@Schema(//
			description = "New password", //
			example = "password1234_new"//
	)
	private String newPassword;

}
