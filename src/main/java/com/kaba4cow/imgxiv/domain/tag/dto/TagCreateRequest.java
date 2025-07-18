package com.kaba4cow.imgxiv.domain.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating a new tag")
public class TagCreateRequest {

	@NotBlank(message = "Name is required")
	@Size(max = 32, message = "Name is too long (max 32 characters)")
	@Schema(//
			description = "Unique name", //
			example = "tag_name"//
	)
	private String name;

	@Size(max = 1024, message = "Description is too long (max 1024 characters)")
	@Schema(//
			description = "Description", //
			example = "tag_description"//
	)
	private String description = "";

	@NotNull(message = "Category ID is required")
	@Schema(//
			description = "Category ID", //
			example = "1"//
	)
	private Long categoryId;

}
