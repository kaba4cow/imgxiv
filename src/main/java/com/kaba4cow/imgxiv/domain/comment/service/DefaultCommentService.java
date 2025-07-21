package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentMapper;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;
import com.kaba4cow.imgxiv.util.PersistLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	@Override
	public CommentDto createComment(CommentCreateRequest request, User user) {
		Comment comment = new Comment();
		comment.getPostAndUser().setPost(postRepository.findByIdOrThrow(request.getPostId()));
		comment.getPostAndUser().setUser(user);
		comment.setText(request.getText());
		return commentMapper.mapToDto(PersistLog.log(commentRepository.save(comment)));
	}

	@Override
	public CommentDto editComment(CommentEditRequest request, User user) {
		Comment comment = commentRepository.findByIdOrThrow(request.getId());
		ensureUserIsAuthor(user, comment);
		comment.setText(request.getText());
		return commentMapper.mapToDto(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long id, User user) {
		Comment comment = commentRepository.findByIdOrThrow(id);
		ensureUserIsAuthor(user, comment);
		commentRepository.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long postId) {
		return commentRepository.findByPost(postRepository.findByIdOrThrow(postId)).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

	private void ensureUserIsAuthor(User user, Comment comment) {
		if (!Objects.equals(user.getId(), comment.getPostAndUser().getUser().getId()))
			throw new AccessDeniedException("Access denied. Not an author");
	}

}
