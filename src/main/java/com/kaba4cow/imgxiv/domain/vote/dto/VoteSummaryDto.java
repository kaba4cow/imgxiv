package com.kaba4cow.imgxiv.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoteSummaryDto {

	private Long postId;

	private Long upVoteCount;

	private Long downVoteCount;

}
