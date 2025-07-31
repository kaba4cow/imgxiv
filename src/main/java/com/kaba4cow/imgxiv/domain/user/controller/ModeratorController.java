package com.kaba4cow.imgxiv.domain.user.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.authority.CanManageModerators;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanViewModerators;
import com.kaba4cow.imgxiv.domain.user.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(//
		name = "Moderators", //
		description = """
				Endpoints for managing moderators.
				""")
@RequestMapping("/api/moderators")
public interface ModeratorController {

	@Operation(//
			summary = "Get MODERATOR users", //
			description = """
					Returns MODERATOR users.
					""")
	@CanViewModerators
	@GetMapping
	ResponseEntity<List<UserDto>> getModerators(//
			@PageableDefault(size = 20, direction = Sort.Direction.DESC, sort = "createdAt.timestamp") Pageable pageable//
	);

	@Operation(//
			summary = "Assign MODERATOR role to user", //
			description = """
					Assigns MODERATOR role to specific user.
					""")
	@CanManageModerators
	@PostMapping("/{id}/assign")
	ResponseEntity<Void> assignModerator(//
			@PathVariable Long id//
	);

	@Operation(//
			summary = "Remove MODERATOR role from user", //
			description = """
					Removes MODERATOR role from specific user.
					""")
	@CanManageModerators
	@PostMapping("/{id}/remove")
	ResponseEntity<Void> removeModerator(//
			@PathVariable Long id//
	);

}
