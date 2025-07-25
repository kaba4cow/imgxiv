package com.kaba4cow.imgxiv.domain.vote.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(//
		name = "Votes", //
		description = """
				Post votes endpoints.
				"""//
)
@RequestMapping("/api/posts/{post}/votes")
public interface PostVoteControllerApiDoc {

}
