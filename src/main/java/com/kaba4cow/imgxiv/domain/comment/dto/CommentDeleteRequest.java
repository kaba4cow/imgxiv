package com.kaba4cow.imgxiv.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Request for deleting a comment")
public class CommentDeleteRequest {

	@NotNull(message = "Comment ID is required")
	@Schema(//
			description = "ID of the comment", //
			example = "1"//
	)
	private Long id;

}
