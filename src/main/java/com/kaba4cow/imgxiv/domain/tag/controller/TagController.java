package com.kaba4cow.imgxiv.domain.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanManageTags;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.dto.TagEditRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Tags", //
		description = """
				Endpoints for managing tags.
				"""//
)
@RequestMapping("/api/tags/{id}")
public interface TagController {

	@Operation(//
			summary = "Get tags by ID", //
			description = """
					Returns tag by its ID.
					"""//
	)
	@PermitAll
	@GetMapping
	ResponseEntity<TagDto> getTag(//
			@PathVariable Long id//
	);

	@CanManageTags
	@PatchMapping
	ResponseEntity<TagDto> editTag(//
			@PathVariable Long id, //
			@Valid @RequestBody TagEditRequest request//
	);

}
