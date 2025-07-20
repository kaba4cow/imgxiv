package com.kaba4cow.imgxiv.domain.vote.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.vote.VoteSummary;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class VoteSummaryMapper {

	public VoteSummaryDto mapToDto(VoteSummary voteSummary, Post post) {
		return new VoteSummaryDto(//
				post.getId(), //
				voteSummary.getTotalVoteCount(), //
				voteSummary.getUpVoteCount(), //
				voteSummary.getDownVoteCount()//
		);
	}

}
