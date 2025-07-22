package com.kaba4cow.imgxiv.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentIdRequest {

	@NotNull(message = "Comment ID is required") //
	@Schema(//
			description = "ID of the comment", //
			example = "1"//
	) //
	private Long commentId;

}
