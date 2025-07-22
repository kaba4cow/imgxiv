package com.kaba4cow.imgxiv.domain.vote.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoteSummaryDto {

	private final Long postId;

	private final Long totalVoteCount;

	private final Long upVoteCount;

	private final Long downVoteCount;

}
