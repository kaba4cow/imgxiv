package com.kaba4cow.imgxiv.domain.vote.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractVoteRequest {

	@NotNull(message = "Post ID is required")
	@Schema(//
			description = "Post ID", //
			example = "1"//
	)
	private Long postId;

}
