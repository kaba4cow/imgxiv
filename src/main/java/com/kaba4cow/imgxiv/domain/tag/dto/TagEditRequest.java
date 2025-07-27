package com.kaba4cow.imgxiv.domain.tag.dto;

import com.kaba4cow.imgxiv.common.validation.TagName;

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
@Schema(description = "Request for editing a tag")
public class TagEditRequest {

	@TagName
	@Schema(//
			description = "New tag name", //
			example = "tag_name"//
	)
	private String name;

	@Size(max = 1024, message = "Description is too long (max 1024 characters)")
	@Schema(//
			description = "New description", //
			example = "Description"//
	)
	private String description;

	@Schema(//
			description = "New category ID", //
			example = "1"//
	)
	private Long category;

}
