package com.kaba4cow.imgxiv.domain.comment.security;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.auth.annotation.authority.CanDeleteComment;
import com.kaba4cow.imgxiv.auth.annotation.authority.CanEditComment;
import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentSecurity {

	private final CommentRepository commentRepository;

	@CanEditComment
	public Comment getCommentToEdit(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

	@CanDeleteComment
	public Comment getCommentToDelete(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

}
