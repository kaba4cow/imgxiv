package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanCreateTag;
import com.kaba4cow.imgxiv.common.dto.CategoryIdRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Tags", //
		description = """
				Endpoints for creating and retrieving tags.
				"""//
)
@RequestMapping("/api/tags")
public interface TagControllerApiDoc {

	@Operation(//
			summary = "Create a new tag", //
			description = """
					Creates a new tag with the given name and associated category, then returns its details.
					"""//
	)
	@CanCreateTag
	@PostMapping
	ResponseEntity<TagDto> createTag(//
			@Valid @RequestBody TagCreateRequest request//
	);

	@Operation(//
			summary = "Get all tags", //
			description = """
					Returns a list of all existing tags.
					"""//
	)
	@PermitAll
	@GetMapping("/all")
	ResponseEntity<List<TagDto>> getAllTags();

	@Operation(//
			summary = "Get tags by category", //
			description = """
					Returns all tags that belong to the specified category by its ID.
					"""//
	)
	@PermitAll
	@GetMapping
	ResponseEntity<List<TagDto>> getTagsByCategory(//
			@Valid @ParameterObject CategoryIdRequest request//
	);

}
