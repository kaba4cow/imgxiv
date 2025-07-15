package com.kaba4cow.imgxiv.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryCreateRequest request) {
		return ResponseEntity.ok(categoryService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAll() {
		return ResponseEntity.ok(categoryService.findAll());
	}

}
