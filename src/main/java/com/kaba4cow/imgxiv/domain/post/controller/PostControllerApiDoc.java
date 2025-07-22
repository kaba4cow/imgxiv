package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.auth.annotation.IsAuthenticated;
import com.kaba4cow.imgxiv.auth.annotation.PermitAll;
import com.kaba4cow.imgxiv.common.dto.parameter.PostIdParams;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostEditRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
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
public interface PostControllerApiDoc {

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
			summary = "Load post image", //
			description = """
					Loads image of the specified post
					"""//
	)
	@GetMapping("/image")
	ResponseEntity<Resource> getPostImage(//
			@Valid @ParameterObject PostIdParams params//
	);

	@Operation(//
			summary = "Edit post", //
			description = """
					Edits specified post.
					"""//
	)
	@IsAuthenticated
	@PatchMapping
	ResponseEntity<PostDto> editPost(//
			@Valid @RequestBody PostEditRequest request//
	);

	@Operation(//
			summary = "Delete post", //
			description = """
					Deletes specified post.
					"""//
	)
	@IsAuthenticated
	@DeleteMapping
	ResponseEntity<Void> deletePost(//
			@Valid @ParameterObject PostIdParams params//
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
			@Valid @RequestBody PostQueryRequest request//
	);

}
