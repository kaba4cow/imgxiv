package com.kaba4cow.imgxiv.domain.category.dto;

import com.kaba4cow.imgxiv.domain.category.validation.CategoryName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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

	@CategoryName
	@Schema(//
			description = "New category name", //
			example = "category_name"//
	)
	private String name;

	@Size(max = 1024, message = "Description is too long (max 1024 characters)")
	@Schema(//
			description = "New description", //
			example = "Category description"//
	)
	private String description = "";

}
