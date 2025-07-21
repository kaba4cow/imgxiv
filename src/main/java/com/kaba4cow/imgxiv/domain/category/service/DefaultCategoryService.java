package com.kaba4cow.imgxiv.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.exception.NameConflictException;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCategoryService implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final CategoryMapper categoryMapper;

	@Override
	public CategoryDto create(CategoryCreateRequest request) {
		if (categoryRepository.existsByName(request.getName()))
			throw new NameConflictException("Category with this name already exists");
		Category category = new Category();
		category.getNameAndDescription().setName(request.getName());
		category.getNameAndDescription().setDescription(request.getDescription());
		Category saved = categoryRepository.save(category);
		log.info("Created new category: {}", saved);
		return categoryMapper.mapToDto(saved);
	}

	@Override
	public List<CategoryDto> findAll() {
		return categoryRepository.findAll().stream()//
				.map(categoryMapper::mapToDto)//
				.collect(Collectors.toList());
	}

}
