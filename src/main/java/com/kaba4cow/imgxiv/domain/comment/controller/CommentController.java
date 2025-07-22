package com.kaba4cow.imgxiv.domain.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.auth.annotation.CurrentUser;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.comment.service.CommentService;
import com.kaba4cow.imgxiv.domain.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController implements CommentControllerApiDoc {

	private final CommentService commentService;

	@Override
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentCreateRequest request, @CurrentUser User user) {
		return ResponseEntity.ok(commentService.createComment(request, user));
	}

	@Override
	public ResponseEntity<CommentDto> editComment(@Valid @RequestBody CommentEditRequest request) {
		return ResponseEntity.ok(commentService.editComment(request));
	}

	@Override
	public ResponseEntity<Void> deleteComment(Long id) {
		commentService.deleteComment(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<CommentDto>> getCommentsByPost(Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPost(postId));
	}

}
