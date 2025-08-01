package com.kaba4cow.imgxiv.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Comments", //
		description = """
				Endpoints for managing comments.
				"""//
)
@RequestMapping("/api/comments/{id}")
public interface CommentController {

	@Operation(//
			summary = "Edit comment", //
			description = """
					Edits specified comment.
					"""//
	)
	@IsAuthenticated
	@PatchMapping
	public ResponseEntity<CommentDto> editComment(//
			@PathVariable Long id, //
			@Valid @RequestBody CommentTextRequest request//
	);

	@Operation(//
			summary = "Delete comment", //
			description = """
					Deletes specified comment.
					"""//
	)
	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<Void> deleteComment(//
			@PathVariable Long id//
	);

}
