package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;
import com.kaba4cow.imgxiv.domain.comment.service.PostCommentService;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostCommentControllerImpl implements PostCommentController {

	private final PostCommentService postCommentService;

	@Override
	public ResponseEntity<CommentDto> createCommentOnPost(Long post, CommentTextRequest request, User user) {
		return ResponseEntity.ok(postCommentService.createComment(post, request, user));
	}

	@Override
	public ResponseEntity<List<CommentDto>> getCommentsByPost(Long post, Pageable pageable) {
		return ResponseEntity.ok(postCommentService.getCommentsByPost(post, pageable));
	}

}
