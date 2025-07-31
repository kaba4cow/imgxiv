package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostTagsRequest;
import com.kaba4cow.imgxiv.domain.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(//
		name = "Posts", //
		description = """
				Endpoints for post creation and retrieval.
				"""//
)
@RequestMapping("/api/posts")
public interface PostController {

	@Operation(//
			summary = "Create a new post", //
			description = """
					Creates a new post with the provided tags and returns the post data.
					"""//
	)
	@IsAuthenticated
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<PostDto> createPost(//
			@Valid @ModelAttribute PostCreateRequest request, //
			@CurrentUser User user//
	);

	@Operation(//
			summary = "Get post by ID", //
			description = """
					Returns post by ID.
					"""//
	)
	@GetMapping("/{id}")
	ResponseEntity<PostDto> getPost(//
			@PathVariable Long id//
	);

	@Operation(//
			summary = "Load post image", //
			description = """
					Loads image of the specified post.
					"""//
	)
	@GetMapping("/{id}/image")
	ResponseEntity<Resource> getPostImage(//
			@PathVariable Long id//
	);

	@Operation(//
			summary = "Load post thumbnail", //
			description = """
					Loads thumbnail of the specified post.
					"""//
	)
	@GetMapping("/{id}/thumb")
	ResponseEntity<Resource> getPostThumbnail(//
			@PathVariable Long id//
	);

	@Operation(//
			summary = "Edit post", //
			description = """
					Edits specified post.
					"""//
	)
	@IsAuthenticated
	@PatchMapping("/{id}")
	ResponseEntity<PostDto> editPost(//
			@PathVariable Long id, //
			@Valid @ParameterObject PostTagsRequest request//
	);

	@Operation(//
			summary = "Delete post", //
			description = """
					Deletes specified post.
					"""//
	)
	@IsAuthenticated
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deletePost(//
			@PathVariable Long id//
	);

	@Operation(//
			summary = "Search posts by tag query", //
			description = """
					Performs a tag-based search and returns a list of post data.
					"""//
	)
	@PermitAll
	@PostMapping("/search")
	ResponseEntity<List<PostDto>> searchPosts(//
			@Valid @RequestBody PostQueryRequest request, //
			@PageableDefault(size = 20, direction = Sort.Direction.DESC, sort = "createdAt.timestamp") Pageable pageable//
	);

}
