package com.kaba4cow.imgxiv.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request for creating or editing a comment")
public class CommentTextRequest {

	@NotBlank(message = "Comment text is required")
	@Size(max = 1024, message = "Text is too long (max 1024 characters)")
	@Schema(//
			description = "Text of the comment", //
			example = "Text of the comment"//
	)
	private String text;

}
