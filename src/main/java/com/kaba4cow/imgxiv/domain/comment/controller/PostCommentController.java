package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;
import com.kaba4cow.imgxiv.domain.user.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Post Comments", //
		description = """
				Endpoints for commenting on posts.
				"""//
)
@RequestMapping("/api/posts/{post}/comments")
public interface PostCommentController {

	@Operation(//
			summary = "Create comment", //
			description = """
					Creates a new comment.
					"""//
	)
	@IsAuthenticated
	@PostMapping
	public ResponseEntity<CommentDto> createCommentOnPost(//
			@PathVariable Long post, //
			@Valid @RequestBody CommentTextRequest request, //
			@CurrentUser User user//
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
			@PathVariable Long post, //
			@PageableDefault(size = 20, direction = Sort.Direction.DESC, sort = "createdAt.timestamp") Pageable pageable//
	);

}
