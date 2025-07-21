package com.kaba4cow.imgxiv.domain.comment.security;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.auth.annotation.security.IsCommentDeletable;
import com.kaba4cow.imgxiv.auth.annotation.security.IsCommentEditable;
import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentSecurity {

	private final CommentRepository commentRepository;

	@IsCommentEditable
	public Comment getCommentToEdit(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

	@IsCommentDeletable
	public Comment getCommentToDelete(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

}
