package com.kaba4cow.imgxiv.domain.post.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.post.dto.PostCreateRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostDto;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.dto.PostTagsRequest;
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
	public ResponseEntity<PostDto> getPost(Long id) {
		return ResponseEntity.ok(postService.getPost(id));
	}

	@Override
	public ResponseEntity<Resource> getPostImage(Long id) {
		return createImageResponse(postService.getPostImage(id));
	}

	@Override
	public ResponseEntity<Resource> getPostThumbnail(Long id) {
		return createImageResponse(postService.getPostThumbnail(id));
	}

	private ResponseEntity<Resource> createImageResponse(ImageResource image) {
		return ResponseEntity.ok()//
				.cacheControl(CacheControl.noStore())//
				.contentLength(image.contentLength())//
				.contentType(image.contentType())//
				.body(image);
	}

	@Override
	public ResponseEntity<PostDto> editPost(Long id, PostTagsRequest request) {
		return ResponseEntity.ok(postService.editPost(id, request.getTags()));
	}

	@Override
	public ResponseEntity<Void> deletePost(Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<PostDto>> searchPosts(PostQueryRequest request, Pageable pageable) {
		return ResponseEntity.ok(postService.findPostsByQuery(request, pageable));
	}

}
