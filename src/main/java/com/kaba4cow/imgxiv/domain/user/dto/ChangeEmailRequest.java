package com.kaba4cow.imgxiv.domain.user.dto;

import com.kaba4cow.imgxiv.domain.user.validation.ValidEmail;

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
public class ChangeEmailRequest {

	@NotBlank(message = "Email is required")
	@ValidEmail
	@Schema(//
			description = "User email", //
			example = "john@example.com"//
	)
	private String email;

}
