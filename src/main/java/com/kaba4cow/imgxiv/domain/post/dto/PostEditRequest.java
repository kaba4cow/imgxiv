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
@Schema(description = "Request for editing an existing post")
public class PostEditRequest {

	@NotNull(message = "Post ID is required")
	@Schema(//
			description = "ID of the post", //
			example = "1" //
	)
	private Long id;

	@NotEmpty(message = "At least one tag is required")
	@Schema(//
			description = "List of new tag IDs", //
			example = "[1, 2, 3]" //
	)
	private List<@NotNull Long> tagIds;

}
