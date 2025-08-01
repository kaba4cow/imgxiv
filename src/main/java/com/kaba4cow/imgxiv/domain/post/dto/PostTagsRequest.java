package com.kaba4cow.imgxiv.domain.post.dto;

import java.util.List;

import com.kaba4cow.imgxiv.domain.tag.validation.ValidTagName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostTagsRequest {

	@NotEmpty(message = "At least one tag is required")
	@Schema(//
			description = "List of tag names"//
	)
	private List<@NotBlank(message = "Tag name is required") @ValidTagName String> tags;

}
