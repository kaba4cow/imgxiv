package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.common.dto.parameter.CommentIdRequest;
import com.kaba4cow.imgxiv.common.dto.parameter.PostIdParams;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.comment.service.CommentService;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController implements CommentControllerApiDoc {

	private final CommentService commentService;

	@Override
	public ResponseEntity<CommentDto> createComment(CommentCreateRequest request, User user) {
		return ResponseEntity.ok(commentService.createComment(request, user));
	}

	@Override
	public ResponseEntity<CommentDto> editComment(CommentEditRequest request) {
		return ResponseEntity.ok(commentService.editComment(request));
	}

	@Override
	public ResponseEntity<Void> deleteComment(CommentIdRequest request) {
		commentService.deleteComment(request.getCommentId());
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<CommentDto>> getCommentsByPost(PostIdParams request) {
		return ResponseEntity.ok(commentService.getCommentsByPost(request.getId()));
	}

}
