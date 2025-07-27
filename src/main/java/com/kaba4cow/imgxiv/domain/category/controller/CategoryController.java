package com.kaba4cow.imgxiv.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryEditRequest;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoryControllerApiDoc {

	private final CategoryService categoryService;

	@Override
	public ResponseEntity<CategoryDto> createCategory(CategoryCreateRequest request) {
		return ResponseEntity.ok(categoryService.createCategory(request));
	}

	@Override
	public ResponseEntity<CategoryDto> editCategory(Long id, @Valid CategoryEditRequest request) {
		return ResponseEntity.ok(categoryService.editCategory(id, request));
	}

	@Override
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(categoryService.findAll());
	}

}
