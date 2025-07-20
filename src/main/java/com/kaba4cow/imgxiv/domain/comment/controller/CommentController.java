package com.kaba4cow.imgxiv.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.common.controller.CurrentUserAwareController;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDeleteRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class CommentController extends CurrentUserAwareController {

	@Operation(//
			summary = "Create comment", //
			description = "Creates a new comment"//
	)
	@IsAuthenticated
	@PostMapping
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentCreateRequest request) {
		throw new UnsupportedOperationException();
	}

	@Operation(//
			summary = "Edit comment", //
			description = "Edits specified comment"//
	)
	@IsAuthenticated
	@PatchMapping
	public ResponseEntity<CommentDto> editComment(@Valid @RequestBody CommentEditRequest request) {
		throw new UnsupportedOperationException();
	}

	@Operation(//
			summary = "Deletes comment", //
			description = "Deletes specified comment"//
	)
	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<CommentDto> deleteComment(@Valid @RequestBody CommentDeleteRequest request) {
		throw new UnsupportedOperationException();
	}

}
