package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.PostIdRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostEditRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.service.PostService;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.image.ImageResource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController implements PostControllerApiDoc {

	private final PostService postService;

	@Override
	public ResponseEntity<PostDto> createPost(PostCreateRequest request, User user) {
		return ResponseEntity.ok(postService.createPost(request, user));
	}

	@Override
	public ResponseEntity<PostDto> getPost(PostIdRequest request) {
		return ResponseEntity.ok(postService.getPost(request.getPostId()));
	}

	@Override
	public ResponseEntity<Resource> getPostImage(PostIdRequest request) {
		return createImageResponse(postService.getPostImage(request.getPostId()));
	}

	@Override
	public ResponseEntity<Resource> getPostThumbnail(PostIdRequest request) {
		return createImageResponse(postService.getPostThumbnail(request.getPostId()));
	}

	private ResponseEntity<Resource> createImageResponse(ImageResource image) {
		return ResponseEntity.ok()//
				.cacheControl(CacheControl.noStore())//
				.contentLength(image.contentLength())//
				.contentType(image.contentType())//
				.body(image);
	}

	@Override
	public ResponseEntity<PostDto> editPost(PostEditRequest request) {
		return ResponseEntity.ok(postService.editPost(request));
	}

	@Override
	public ResponseEntity<Void> deletePost(PostIdRequest request) {
		postService.deletePost(request.getPostId());
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<PostDto>> searchPosts(PostQueryRequest request) {
		return ResponseEntity.ok(postService.findPostsByQuery(request));
	}

}
