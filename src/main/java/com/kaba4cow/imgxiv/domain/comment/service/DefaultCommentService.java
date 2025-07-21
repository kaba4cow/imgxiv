package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentMapper;
import com.kaba4cow.imgxiv.domain.comment.security.CommentSecurity;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final CommentSecurity commentSecurity;

	private final CommentMapper commentMapper;

	@Override
	public CommentDto createComment(CommentCreateRequest request, User author) {
		Comment comment = new Comment();
		comment.setPost(postRepository.findByIdOrThrow(request.getPostId()));
		comment.setAuthor(author);
		comment.setText(request.getText());
		Comment saved = commentRepository.save(comment);
		log.info("Created new comment: {}", saved);
		return commentMapper.mapToDto(saved);
	}

	@Override
	public CommentDto editComment(CommentEditRequest request) {
		Comment comment = commentSecurity.getCommentToEdit(request.getId());
		comment.setText(request.getText());
		return commentMapper.mapToDto(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long id) {
		Comment comment = commentSecurity.getCommentToDelete(id);
		commentRepository.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long postId) {
		return commentRepository.findByPost(postRepository.findByIdOrThrow(postId)).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

}
