package com.kaba4cow.imgxiv.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentIdRequest {

	@NotNull(message = "Comment ID is required") //
	@Schema(//
			description = "ID of the comment", //
			example = "1"//
	) //
	private Long commentId;

}
