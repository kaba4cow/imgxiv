package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostVoteController implements VoteControllerApiDoc {

	private final VoteService voteService;

}
