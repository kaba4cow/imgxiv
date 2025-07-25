package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.VoteType;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(//
		name = "Votes", //
		description = """
				Endpoints for voting on posts.
				"""//
)
@RequestMapping("/api/posts/{post}/votes")
public interface PostVoteControllerApiDoc {

	@Operation(//
			summary = "Create or update vote", //
			description = """
					Creates a new vote or replaces an existing one by the current user for the specified post.
					"""//
	)
	@IsAuthenticated
	@PostMapping
	ResponseEntity<Void> createVote(//
			@PathVariable Long post, //
			@RequestParam VoteType type, //
			@CurrentUser User user//
	);

	@Operation(//
			summary = "Delete vote", //
			description = """
					Deletes the current user's vote for the specified post.
					"""//
	)
	@IsAuthenticated
	@DeleteMapping
	ResponseEntity<Void> deleteVote(//
			@PathVariable Long post, //
			@CurrentUser User user//
	);

	@Operation(//
			summary = "Get vote summary", //
			description = """
					Returns the total, up, and down vote counts for a specific post.
					"""//
	)
	@PermitAll
	@GetMapping
	ResponseEntity<VoteSummaryDto> getVoteSummary(//
			@PathVariable Long post//
	);

}
