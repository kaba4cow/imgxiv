package com.kaba4cow.imgxiv.domain.tag.dto;

import com.kaba4cow.imgxiv.domain.tag.validation.ValidTagDescription;
import com.kaba4cow.imgxiv.domain.tag.validation.ValidTagName;

import io.swagger.v3.oas.annotations.media.Schema;
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

	@ValidTagName
	@Schema(//
			description = "New tag name", //
			example = "tag_name"//
	)
	private String name;

	@ValidTagDescription
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
