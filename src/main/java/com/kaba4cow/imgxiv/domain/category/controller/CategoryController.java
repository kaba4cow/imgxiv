package com.kaba4cow.imgxiv.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanCreateCategory;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanManageCategories;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryEditRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Categories", //
		description = """
				Endpoints for managing tag categories.
				"""//
)
@RequestMapping("/api/categories")
public interface CategoryController {

	@Operation(//
			summary = "Create a new category", //
			description = """
					Creates a new tag category and returns its details.
					"""//
	)
	@CanCreateCategory
	@PostMapping
	ResponseEntity<CategoryDto> createCategory(//
			@Valid @RequestBody CategoryCreateRequest request//
	);

	@Operation(//
			summary = "Edits an existing category", //
			description = """
					Edits the category and returns its details.
					"""//
	)
	@CanManageCategories
	@PatchMapping("/{id}")
	ResponseEntity<CategoryDto> editCategory(//
			@PathVariable Long id, //
			@Valid @RequestBody CategoryEditRequest request//
	);

	@Operation(//
			summary = "Get all categories", //
			description = """
					Returns a list of all available tag categories.
					"""//
	)
	@PermitAll
	@GetMapping("/all")
	ResponseEntity<List<CategoryDto>> getAllCategories();

}
