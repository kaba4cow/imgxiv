package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.common.controller.CurrentUserAwareController;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteDeleteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/votes")
@RestController
public class VoteController extends CurrentUserAwareController {

	private final VoteService voteService;

	@IsAuthenticated
	@PostMapping
	public ResponseEntity<Void> createVote(@Valid @RequestBody VoteCreateRequest request, @ModelAttribute User currentUser) {
		voteService.createVote(request, currentUser);
		return ResponseEntity.noContent().build();
	}

	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<Void> deleteVote(@Valid @RequestBody VoteDeleteRequest request, @ModelAttribute User currentUser) {
		voteService.deleteVote(request, currentUser);
		return ResponseEntity.noContent().build();
	}

	@PermitAll
	@GetMapping
	public ResponseEntity<VoteSummaryDto> getVoteSummary(@Valid @RequestBody VoteRequest request) {
		return ResponseEntity.ok(voteService.getVoteSummary(request));
	}

}
