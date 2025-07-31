package com.kaba4cow.imgxiv.domain.category.dto;

import com.kaba4cow.imgxiv.domain.category.validation.ValidCategoryDescription;
import com.kaba4cow.imgxiv.domain.category.validation.ValidCategoryName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for editing a category")
public class CategoryEditRequest {

	@ValidCategoryName
	@Schema(//
			description = "New category name", //
			example = "category_name"//
	)
	private String name;

	@ValidCategoryDescription
	@Schema(//
			description = "New description", //
			example = "Category description"//
	)
	private String description = "";

}
