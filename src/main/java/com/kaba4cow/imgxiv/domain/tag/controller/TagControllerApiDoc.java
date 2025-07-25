package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.common.dto.CategoryIdRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Tags", //
		description = """
				Endpoints for retrieving tags.
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

	@Operation(//
			summary = "Get tags by category", //
			description = """
					Returns all tags that belong to the specified category by its ID.
					"""//
	)
	@PermitAll
	@GetMapping("/categorized")
	ResponseEntity<List<TagDto>> getCategorizedTags(//
			@Valid @ParameterObject CategoryIdRequest request//
	);

	@Operation(//
			summary = "Get uncategorized tags", //
			description = """
					Returns all tags that don't belong to any category.
					"""//
	)
	@PermitAll
	@GetMapping("/uncategorized")
	ResponseEntity<List<TagDto>> getUncategorizedTags();

}
