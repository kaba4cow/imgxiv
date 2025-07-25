package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteTypeRequest;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VoteController implements VoteControllerApiDoc {

	private final VoteService voteService;

	@Override
	public ResponseEntity<Void> createVote(Long post, VoteTypeRequest request, User user) {
		voteService.createVote(post, request, user);
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
