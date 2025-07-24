package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.service.PostCommentService;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostCommentController implements PostCommentControllerApiDoc {

	private final PostCommentService postCommentService;

	@Override
	public ResponseEntity<CommentDto> createCommentOnPost(Long id, CommentCreateRequest request, User user) {
		return ResponseEntity.ok(postCommentService.createComment(id, request, user));
	}

	@Override
	public ResponseEntity<List<CommentDto>> getCommentsByPost(Long id) {
		return ResponseEntity.ok(postCommentService.getCommentsByPost(id));
	}

}
