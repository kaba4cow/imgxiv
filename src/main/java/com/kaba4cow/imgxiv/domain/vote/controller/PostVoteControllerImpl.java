package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.VoteType;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostVoteControllerImpl implements PostVoteController {

	private final VoteService voteService;

	@Override
	public ResponseEntity<Void> createVote(Long post, VoteType type, User user) {
		voteService.createVote(post, type, user);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteVote(Long post, User user) {
		voteService.deleteVote(post, user);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<VoteSummaryDto> getVoteSummary(Long post) {
		return ResponseEntity.ok(voteService.getVoteSummary(post));
	}

}
