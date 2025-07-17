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
public class ChangeUsernameRequest {

	@NotBlank(message = "Username is required")
	@Size(min = 2, message = "Username is too short (min 4 characters)")
	@Size(max = 32, message = "Username is too long (max 50 characters)")
	@Schema(//
			description = "Unique username", //
			example = "john_doe"//
	)
	private String username;

}
