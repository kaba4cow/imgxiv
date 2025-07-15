package com.kaba4cow.imgxiv.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryCreateRequest {

	@NotBlank(message = "Name is required")
	@Size(min = 2, message = "Name is too short (min 2 characters)")
	@Size(max = 16, message = "Name is too long (max 16 characters)")
	private String name;

	@NotBlank(message = "Description is required")
	@Size(min = 16, message = "Description is too short (min 16 characters)")
	@Size(max = 1024, message = "Description is too long (max 1024 characters)")
	private String description;

}
