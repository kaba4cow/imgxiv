package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteDeleteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Votes", //
		description = "Endpoints for voting on posts"//
)
@RequestMapping("/api/votes")
@RestController
public class VoteController {

	private final VoteService voteService;

	@Operation(//
			summary = "Create or update vote", //
			description = "Creates a new vote or replaces an existing one by the current user for the specified post"//
	)
	@IsAuthenticated
	@PostMapping
	public ResponseEntity<Void> createVote(@Valid @RequestBody VoteCreateRequest request, @CurrentUser User user) {
		voteService.createVote(request, user);
		return ResponseEntity.noContent().build();
	}

	@Operation(//
			summary = "Delete vote", //
			description = "Deletes the current user's vote for the specified post"//
	)
	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<Void> deleteVote(@Valid @RequestBody VoteDeleteRequest request, @CurrentUser User user) {
		voteService.deleteVote(request, user);
		return ResponseEntity.noContent().build();
	}

	@Operation(//
			summary = "Get vote summary", //
			description = "Returns the total, up, and down vote counts for a specific post"//
	)
	@PermitAll
	@GetMapping
	public ResponseEntity<VoteSummaryDto> getVoteSummary(@Valid @RequestBody VoteRequest request) {
		return ResponseEntity.ok(voteService.getVoteSummary(request));
	}

}
