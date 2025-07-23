package com.kaba4cow.imgxiv.domain.user.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.authority.CanManageModerators;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanViewModerators;
import com.kaba4cow.imgxiv.common.dto.PageableRequest;
import com.kaba4cow.imgxiv.common.dto.UserIdRequest;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Moderators", //
		description = """
				Endpoints for managing moderators.
				""")
@RequestMapping("/api/moderators")
public interface ModeratorControllerApiDoc {

	@Operation(//
			summary = "Get MODERATOR users", //
			description = """
					Returns MODERATOR users.
					""")
	@CanViewModerators
	@GetMapping
	ResponseEntity<List<UserDto>> getModerators(//
			@Valid @RequestBody PageableRequest request//
	);

	@Operation(//
			summary = "Assign MODERATOR role to user", //
			description = """
					Assigns MODERATOR role to specific user.
					""")
	@CanManageModerators
	@PostMapping("/assign")
	ResponseEntity<Void> assignModerator(//
			@Valid @ParameterObject UserIdRequest request//
	);

	@Operation(//
			summary = "Remove MODERATOR role from user", //
			description = """
					Removes MODERATOR role from specific user.
					""")
	@CanManageModerators
	@PostMapping("/remove")
	ResponseEntity<Void> removeModerator(//
			@Valid @ParameterObject UserIdRequest request//
	);

}
