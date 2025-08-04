package com.kaba4cow.imgxiv.domain.comment.service;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentMapper;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;
import com.kaba4cow.imgxiv.domain.comment.model.Comment;
import com.kaba4cow.imgxiv.domain.comment.security.CommentSecurity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

	private final CommentRepository commentRepository;

	private final CommentSecurity commentSecurity;

	private final CommentMapper commentMapper;

	@Override
	public CommentDto editComment(Long id, CommentTextRequest request) {
		Comment comment = commentSecurity.getCommentToEdit(id);
		comment.setText(request.getText());
		Comment saved = commentRepository.save(comment);
		log.info("Edited comment: {}", saved);
		return commentMapper.mapToDto(saved);
	}

	@Override
	public void deleteComment(Long id) {
		Comment comment = commentSecurity.getCommentToDelete(id);
		commentRepository.delete(comment);
		log.info("Deleted comment: {}", comment);
	}

}
