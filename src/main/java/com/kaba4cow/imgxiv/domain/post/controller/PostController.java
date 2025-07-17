package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		description = "Post creation"//
)
@RequestMapping("/api/posts")
@RestController
public class PostController {

	private final PostService postService;

	private final CurrentUserService currentUserService;

	@Operation(//
			summary = "Creates new post", //
			description = "Creates a new post with given tags and returns post info"//
	)
	@PreAuthorize("hasAuthority('create-post')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostCreateRequest request) {
		User user = currentUserService.getUserOrThrow();
		return ResponseEntity.ok(postService.create(request, user));
	}

	@Operation(//
			summary = "Retrieves posts by specified query", //
			description = "Returns post previews of posts by specified query"//
	)
	@GetMapping
	public ResponseEntity<List<PostPreviewDto>> getPostsByQuery(@RequestBody @Valid PostQueryRequest request) {
		return ResponseEntity.ok(postService.findByQuery(request));
	}

	@Operation(//
			summary = "Retrieves all existing posts", //
			description = "Returns post infos of all existing posts"//
	)
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		return ResponseEntity.ok(postService.findAll());
	}

}
