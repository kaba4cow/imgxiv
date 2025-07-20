package com.kaba4cow.imgxiv.domain.vote.dto;

import com.kaba4cow.imgxiv.domain.vote.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request for creating an UP_VOTE vote")
public class UpVoteCreateRequest extends AbstractVoteRequest {

	public VoteType getVoteType() {
		return VoteType.UP_VOTE;
	}

}
