package com.kaba4cow.imgxiv.domain.category.dto;

import com.kaba4cow.imgxiv.domain.category.validation.ValidCategoryDescription;
import com.kaba4cow.imgxiv.domain.category.validation.ValidCategoryName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating a new category")
public class CategoryCreateRequest {

	@NotBlank(message = "Category name is required")
	@ValidCategoryName
	@Schema(//
			description = "Unique name", //
			example = "category_name"//
	)
	private String name;

	@ValidCategoryDescription
	@Schema(//
			description = "Description", //
			example = "Category description"//
	)
	private String description = "";

}
