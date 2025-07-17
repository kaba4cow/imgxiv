package com.kaba4cow.imgxiv.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		description = "Category creation"//
)
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	@Operation(//
			summary = "Creates new category", //
			description = "Creates a new category and returns category info"//
	)
	@PreAuthorize("hasAuthority('create-category')")
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
		return ResponseEntity.ok(categoryService.create(request));
	}

	@Operation(//
			summary = "Retrieves all existing categories", //
			description = "Returns category infos of all existing categories"//
	)
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(categoryService.findAll());
	}

}
