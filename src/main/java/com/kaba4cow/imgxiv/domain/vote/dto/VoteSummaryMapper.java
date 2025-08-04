package com.kaba4cow.imgxiv.domain.vote.dto;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.vote.model.VoteSummary;

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
