package com.kaba4cow.imgxiv.domain.user.dto;

import com.kaba4cow.imgxiv.domain.user.validation.ValidUsername;

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
public class ChangeUsernameRequest {

	@NotBlank(message = "Username is required")
	@ValidUsername
	@Schema(//
			description = "Unique username", //
			example = "john_doe"//
	)
	private String username;

}
