package com.kaba4cow.imgxiv.domain.comment.dto;

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
@Schema(description = "Request for editing a comment")
public class CommentEditRequest {

	@NotNull(message = "Comment ID is required")
	@Schema(//
			description = "ID of the comment", //
			example = "1"//
	)
	private Long id;

	@NotBlank(message = "Comment text is required")
	@Size(max = 1024, message = "Text is too long (max 1024 characters)")
	@Schema(//
			description = "Text of the comment", //
			example = "Text of the comment"//
	)
	private String text;

}
