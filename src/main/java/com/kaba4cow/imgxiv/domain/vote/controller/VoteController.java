package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.parameter.PostIdParams;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VoteController implements VoteControllerApiDoc {

	private final VoteService voteService;

	@Override
	public ResponseEntity<Void> createVote(VoteCreateRequest request, User user) {
		voteService.createVote(request, user);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> deleteVote(PostIdParams request, User user) {
		voteService.deleteVote(request.getId(), user);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<VoteSummaryDto> getVoteSummary(PostIdParams request) {
		return ResponseEntity.ok(voteService.getVoteSummary(request.getId()));
	}

}
