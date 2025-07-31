package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(//
		name = "Category Tags", //
		description = """
				Endpoints for retrieving tags by categories.
				"""//
)
@RequestMapping("/api/categories")
public interface CategoryTagController {

	@Operation(//
			summary = "Get tags by category", //
			description = """
					Returns all tags that belong to the specified category by its ID.
					"""//
	)
	@PermitAll
	@GetMapping("/{category}/tags")
	ResponseEntity<List<TagDto>> getCategorizedTags(//
			@PathVariable Long category//
	);

	@Operation(//
			summary = "Get uncategorized tags", //
			description = """
					Returns all tags that don't belong to any category.
					"""//
	)
	@PermitAll
	@GetMapping("/tags")
	ResponseEntity<List<TagDto>> getUncategorizedTags();

}
