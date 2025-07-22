package com.kaba4cow.imgxiv.domain.vote.dto;

import com.kaba4cow.imgxiv.common.dto.parameter.PostIdRequest;
import com.kaba4cow.imgxiv.domain.vote.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating a vote")
public class VoteCreateRequest extends PostIdRequest {

	@NotNull(message = "Vote type is required")
	@Schema(//
			description = "Vote type", //
			example = "UP"//
	)
	private VoteType type;

}
