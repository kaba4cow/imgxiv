package com.kaba4cow.imgxiv.domain.category.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryEditRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCategoryService implements CategoryService {

	public static final String DEFAULT_CATEGORY_NAME = "Uncategorized";

	private final CategoryRepository categoryRepository;

	private final CategoryMapper categoryMapper;

	@Override
	public CategoryDto createCategory(CategoryCreateRequest request) {
		if (categoryRepository.existsByName(request.getName()))
			throw new NameConflictException("Category with this name already exists");
		Category saved = categoryRepository.save(Category.builder()//
				.name(request.getName())//
				.description(request.getDescription())//
				.build());
		log.info("Created new category: {}", saved);
		return categoryMapper.mapToDto(saved);
	}

	@Override
	public CategoryDto editCategory(Long id, CategoryEditRequest request) {
		Category category = categoryRepository.findByIdOrThrow(id);
		if (!Objects.equals(category.getName(), request.getName()) && categoryRepository.existsByName(request.getName()))
			throw new NameConflictException("Category with this name already exists");
		Optional.ofNullable(request.getName())//
				.ifPresent(category::setName);
		Optional.ofNullable(request.getDescription())//
				.ifPresent(category::setDescription);
		Category saved = categoryRepository.save(category);
		log.info("Updated category: {}", saved);
		return categoryMapper.mapToDto(saved);
	}

	@Override
	public List<CategoryDto> findAll() {
		return categoryRepository.findAll().stream()//
				.map(categoryMapper::mapToDto)//
				.collect(Collectors.toList());
	}

	@Override
	public Category getDefaultCategory() {
		return categoryRepository.findByName(DEFAULT_CATEGORY_NAME).orElseGet(this::createDefaultCategory);
	}

	private Category createDefaultCategory() {
		Category saved = categoryRepository.save(Category.builder()//
				.name(DEFAULT_CATEGORY_NAME)//
				.description(DEFAULT_CATEGORY_NAME)//
				.build());
		log.info("Created default category: {}", saved);
		return saved;
	}

}
