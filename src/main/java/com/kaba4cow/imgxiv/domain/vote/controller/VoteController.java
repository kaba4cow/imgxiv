package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.context.CurrentUserService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteCreateRequest;
import com.kaba4cow.imgxiv.domain.vote.dto.VoteDeleteRequest;
import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/votes")
@RestController
public class VoteController {

	private final CurrentUserService currentUserService;

	private final VoteService voteService;

	@IsAuthenticated
	@PostMapping
	public ResponseEntity<Void> createVote(@Valid @RequestBody VoteCreateRequest request) {
		voteService.createVote(request, getUser());
		return ResponseEntity.noContent().build();
	}

	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<Void> deleteVote(@Valid @RequestBody VoteDeleteRequest request) {
		voteService.deleteVote(request, getUser());
		return ResponseEntity.noContent().build();
	}

	private User getUser() {
		return currentUserService.getUserOrThrow();
	}

}
