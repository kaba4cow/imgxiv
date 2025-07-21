package com.kaba4cow.imgxiv.domain.comment.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentSecurity {

	private final CommentRepository commentRepository;

	@PostAuthorize("returnObject.author.id == principal.id")
	public Comment getCommentToEdit(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

	@PostAuthorize("returnObject.author.id == principal.id or hasRole('moderator')")
	public Comment getCommentToDelete(Long id) {
		return commentRepository.findByIdOrThrow(id);
	}

}
