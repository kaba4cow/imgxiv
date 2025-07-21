package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostEditRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.service.PostService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.image.ImageResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

	@Operation(//
			summary = "Create a new post", //
			description = "Creates a new post with the provided tags and returns the post data"//
	)
	@IsAuthenticated
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostDto> createPost(@Valid @ModelAttribute PostCreateRequest request, @CurrentUser User user) {
		return ResponseEntity.ok(postService.createPost(request, user));
	}

	@Operation(//
			summary = "Load post image", //
			description = "Loads image of the specified post"//
	)
	@GetMapping("/image")
	public ResponseEntity<Resource> getPostImage(//
			@NotNull(message = "Post ID is required") //
			@Schema(//
					description = "ID of the post", //
					example = "1"//
			) //
			@RequestParam("id") Long id) {
		ImageResource image = postService.getPostImage(id);
		return ResponseEntity.ok()//
				.cacheControl(CacheControl.maxAge(1L, TimeUnit.HOURS).cachePublic())//
				.contentLength(image.contentLength())//
				.contentType(image.contentType())//
				.body(image);
	}

	@Operation(//
			summary = "Edit post", //
			description = "Edits specified post"//
	)
	@IsAuthenticated
	@PatchMapping
	public ResponseEntity<PostDto> editPost(@Valid @RequestBody PostEditRequest request) {
		return ResponseEntity.ok(postService.editPost(request));
	}

	@Operation(//
			summary = "Delete post", //
			description = "Deletes specified post"//
	)
	@IsAuthenticated
	@DeleteMapping
	public ResponseEntity<Void> deletePost(//
			@NotNull(message = "Post ID is required") //
			@Schema(//
					description = "ID of the post", //
					example = "1"//
			) //
			@RequestParam("id") Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(//
			summary = "Search posts by tag query", //
			description = "Performs a tag-based search and returns a list of post data"//
	)
	@PermitAll
	@PostMapping("/search")
	public ResponseEntity<List<PostDto>> searchPosts(@Valid @RequestBody PostQueryRequest request) {
		return ResponseEntity.ok(postService.findPostsByQuery(request));
	}

}
