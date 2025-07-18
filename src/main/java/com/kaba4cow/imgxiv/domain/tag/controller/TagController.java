package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanCreateTag;
import com.kaba4cow.imgxiv.domain.tag.dto.TagCreateRequest;
import com.kaba4cow.imgxiv.domain.tag.dto.TagDto;
import com.kaba4cow.imgxiv.domain.tag.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Tags", //
		description = "Endpoints for creating and retrieving tags"//
)
@RequestMapping("/api/tags")
@RestController
public class TagController {

	private final TagService tagService;

	@Operation(//
			summary = "Create a new tag", //
			description = "Creates a new tag with the given name and associated category, then returns its details"//
	)
	@CanCreateTag
	@PostMapping
	public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagCreateRequest request) {
		return ResponseEntity.ok(tagService.create(request));
	}

	@Operation(//
			summary = "Get all tags", //
			description = "Returns a list of all existing tags"//
	)
	@PermitAll
	@GetMapping("/all")
	public ResponseEntity<List<TagDto>> getAllTags() {
		return ResponseEntity.ok(tagService.findAll());
	}

	@Operation(//
			summary = "Get tags by category", //
			description = "Returns all tags that belong to the specified category by its ID"//
	)
	@PermitAll
	@GetMapping
	public ResponseEntity<List<TagDto>> getTagsByCategory(//
			@Schema(//
					description = "ID of the category to filter tags by", //
					example = "1"//
			) //
			@RequestParam("categoryId") @NotNull Long categoryId) {
		return ResponseEntity.ok(tagService.findByCategoryId(categoryId));
	}

}
