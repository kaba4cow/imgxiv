package com.kaba4cow.imgxiv.domain.post.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating a new post")
public class PostCreateRequest {

	@NotEmpty(message = "At least one tag is required")
	@Schema(//
			description = "List of tag IDs", //
			example = "[1, 2, 3]" //
	)
	private List<@NotNull Long> tagIds;

}
