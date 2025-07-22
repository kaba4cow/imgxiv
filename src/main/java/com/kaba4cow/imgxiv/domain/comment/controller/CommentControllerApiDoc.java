package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.common.dto.parameter.CommentIdRequest;
import com.kaba4cow.imgxiv.common.dto.parameter.PostIdRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Comments", //
		description = """
				Endpoints for commenting on posts.
				"""//
)
@RequestMapping("/api/comments")
public interface CommentControllerApiDoc {

	@Operation(//
			summary = "Create comment", //
			description = """
					Creates a new comment.
					"""//
	)
	@IsAuthenticated
	@PostMapping
	public ResponseEntity<CommentDto> createComment(//
			@Valid @RequestBody CommentCreateRequest request, //
			@CurrentUser User user//
	);

	@Operation(//
			summary = "Edit comment", //
			description = """
					Edits specified comment.
					"""//
	)
	@IsAuthenticated
	@PatchMapping
	public ResponseEntity<CommentDto> editComment(//
			@Valid @RequestBody CommentEditRequest request//
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
			@Valid @ParameterObject CommentIdRequest request//
	);

	@Operation(//
			summary = "Get comments of specified post", //
			description = """
					Retrieves comments of specified post.
					"""//
	)
	@PermitAll
	@GetMapping
	public ResponseEntity<List<CommentDto>> getCommentsByPost(//
			@Valid @ParameterObject PostIdRequest request//
	);

}
