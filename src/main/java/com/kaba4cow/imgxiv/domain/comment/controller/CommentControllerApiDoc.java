package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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

			@Valid //
			@RequestBody //
			CommentCreateRequest request, //

			@CurrentUser //
			User user//

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

			@Valid //
			@RequestBody //
			CommentEditRequest request//

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

			@NotNull(message = "Comment ID is required") //
			@Schema(//
					description = "ID of the comment", //
					example = "1"//
			) //
			@RequestParam("id") //
			Long id//

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

			@NotNull(message = "Post ID is required") //
			@Schema(//
					description = "ID of the post", //
					example = "1"//
			) //
			@RequestParam("postId") //
			Long postId//

	);

}
