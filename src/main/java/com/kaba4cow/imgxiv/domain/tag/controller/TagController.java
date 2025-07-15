package com.kaba4cow.imgxiv.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		description = "Tag creation"//
)
@RequestMapping("/api/tags")
@RestController
public class TagController {

	private final TagService tagService;

	@Operation(//
			summary = "Creates new tag", //
			description = "Creates a new tag and returns tag info"//
	)
	@PreAuthorize("hasRole('create-tag')")
	@PostMapping
	public ResponseEntity<TagDto> create(//
			@RequestBody //
			@Valid //
			TagCreateRequest request) {
		return ResponseEntity.ok(tagService.create(request));
	}

	@Operation(//
			summary = "Retrieves all tags of certain category", //
			description = "Returns tag infos of all existing tags by category ID"//
	)
	@GetMapping
	public ResponseEntity<List<TagDto>> getByCategory(//
			@Schema(//
					description = "ID of the category to filter tags by", //
					example = "1234567890"//
			) //
			@RequestParam("category") //
			@NotNull //
			Long categoryId) {
		return ResponseEntity.ok(tagService.findByCategoryId(categoryId));
	}

}
