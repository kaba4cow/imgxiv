package com.kaba4cow.imgxiv.domain.category.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryDto;

public interface CategoryService {

	CategoryDto create(CategoryCreateRequest request);

	List<Category> findAll();

}
