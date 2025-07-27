package com.kaba4cow.imgxiv.domain.category.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryEditRequest;

public interface CategoryService {

	CategoryDto createCategory(CategoryCreateRequest request);

	CategoryDto editCategory(Long id, CategoryEditRequest request);

	List<CategoryDto> findAll();

	Category getDefaultCategory();

}
