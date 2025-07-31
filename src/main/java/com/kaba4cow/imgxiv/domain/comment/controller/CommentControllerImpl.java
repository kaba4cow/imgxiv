package com.kaba4cow.imgxiv.domain.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;
import com.kaba4cow.imgxiv.domain.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentControllerImpl implements CommentController {

	private final CommentService commentService;

	@Override
	public ResponseEntity<CommentDto> editComment(Long id, CommentTextRequest request) {
		return ResponseEntity.ok(commentService.editComment(id, request));
	}

	@Override
	public ResponseEntity<Void> deleteComment(Long id) {
		commentService.deleteComment(id);
		return ResponseEntity.noContent().build();
	}

}
