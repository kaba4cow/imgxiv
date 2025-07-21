package com.kaba4cow.imgxiv.domain.category.factory;

import com.kaba4cow.imgxiv.domain.category.Category;
import com.kaba4cow.imgxiv.domain.category.dto.CategoryCreateRequest;

public interface CategoryFactory {

	Category createCategory(CategoryCreateRequest request);

}
