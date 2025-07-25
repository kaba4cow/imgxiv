package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.common.dto.PostIdRequest;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteTypeRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteSummaryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Votes", //
		description = """
				Endpoints for voting on posts.
				"""//
)
@RequestMapping("/api/votes")
public interface VoteControllerApiDoc {

	@Operation(//
			summary = "Create or update vote", //
			description = """
					Creates a new vote or replaces an existing one by the current user for the specified post.
					"""//
	)
	@IsAuthenticated
	@PostMapping
	ResponseEntity<Void> createVote(//
			@Valid @RequestBody VoteTypeRequest request, //
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
			@Valid @ParameterObject PostIdRequest request, //
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
			@Valid @ParameterObject PostIdRequest request//
	);

}
