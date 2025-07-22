package com.kaba4cow.imgxiv.domain.user.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.common.dto.UserIdRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Users", //
		description = """
				Endpoints for retrieving user information.
				""")
@RequestMapping("/api/users")
public interface UserControllerApiDoc {

	@Operation(//
			summary = "Get user by ID", //
			description = """
					Returns information about specific user.
					""")
	@IsAuthenticated
	@GetMapping
	ResponseEntity<UserDto> getUser(//
			@Valid @ParameterObject UserIdRequest request//
	);

}
