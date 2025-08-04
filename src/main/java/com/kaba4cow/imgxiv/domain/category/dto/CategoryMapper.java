package com.kaba4cow.imgxiv.domain.category.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.category.model.Category;

@Component
public class CategoryMapper {

	public CategoryDto mapToDto(Category category) {
		return new CategoryDto(//
				category.getId(), //
				category.getName(), //
				category.getDescription()//
		);
	}

}
