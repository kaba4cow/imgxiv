package com.kaba4cow.imgxiv.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanCreateCategory;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Categories", //
		description = "Endpoints for managing tag categories"//
)
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	@Operation(//
			summary = "Create a new category", //
			description = "Creates a new tag category and returns its details"//
	)
	@CanCreateCategory
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
		return ResponseEntity.ok(categoryService.create(request));
	}

	@Operation(//
			summary = "Get all categories", //
			description = "Returns a list of all available tag categories"//
	)
	@PermitAll
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(categoryService.findAll());
	}

}
