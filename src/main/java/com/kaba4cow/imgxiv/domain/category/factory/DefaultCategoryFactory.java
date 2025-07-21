package com.kaba4cow.imgxiv.domain.category.factory;

import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.CategoryRepository;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultCategoryFactory implements CategoryFactory {

	private final CategoryRepository categoryRepository;

	@Override
	public Category createCategory(CategoryCreateRequest request) {
		Category category = new Category();
		category.getNameAndDescription().setName(request.getName());
		category.getNameAndDescription().setDescription(request.getDescription());
		Category saved = categoryRepository.save(category);
		log.info("Created new category: {}", saved);
		return saved;
	}

}
