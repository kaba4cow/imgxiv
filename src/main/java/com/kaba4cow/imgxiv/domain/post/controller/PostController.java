package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanCreatePost;
import com.kaba4cow.imgxiv.auth.context.CurrentUserService;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostPreviewDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.service.PostService;
import com.kaba4cow.imgxiv.domain.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(//
		name = "Posts", //
		description = "Endpoints for post creation and retrieval"//
)
@RequestMapping("/api/posts")
@RestController
public class PostController {

	private final PostService postService;

	private final CurrentUserService currentUserService;

	@Operation(//
			summary = "Create a new post", //
			description = "Creates a new post with the provided tags and returns the full post data"//
	)
	@CanCreatePost
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostCreateRequest request) {
		User user = currentUserService.getUserOrThrow();
		return ResponseEntity.ok(postService.create(request, user));
	}

	@Operation(//
			summary = "Search posts by tag query", //
			description = """
					Performs a tag-based search and returns a list of post previews.

					The query string can contain required and excluded tags.
					Example: `"cat cute !boring"` — finds posts tagged with "cat" and "cute" but not "boring".
					"""//
	)
	@PermitAll
	@PostMapping("/search")
	public ResponseEntity<List<PostPreviewDto>> searchPosts(@RequestBody @Valid PostQueryRequest request) {
		return ResponseEntity.ok(postService.findByQuery(request));
	}

	@Operation(//
			summary = "Get all posts", //
			description = "Returns a full list of all existing posts including tag info"//
	)
	@PermitAll
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		return ResponseEntity.ok(postService.findAll());
	}

}
