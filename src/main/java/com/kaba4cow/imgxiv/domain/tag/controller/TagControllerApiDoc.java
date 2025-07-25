package com.kaba4cow.imgxiv.domain.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(//
		name = "Tags", //
		description = """
				Endpoints for managing tags.
				"""//
)
@RequestMapping("/api/tags")
public interface TagControllerApiDoc {

	@Operation(//
			summary = "Get tags by ID", //
			description = """
					Returns tag by its ID.
					"""//
	)
	@PermitAll
	@GetMapping("/{id}")
	ResponseEntity<TagDto> getTag(//
			@PathVariable Long id//
	);

}
