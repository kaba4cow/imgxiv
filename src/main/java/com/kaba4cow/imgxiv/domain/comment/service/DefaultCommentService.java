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
import com.kaba4cow.imgxiv.domain.user.UserRole;
import com.kaba4cow.imgxiv.util.PersistLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	@Override
	public CommentDto createComment(CommentCreateRequest request, User author) {
		Comment comment = new Comment();
		comment.setPost(postRepository.findByIdOrThrow(request.getPostId()));
		comment.setAuthor(author);
		comment.setText(request.getText());
		return commentMapper.mapToDto(PersistLog.log(commentRepository.save(comment)));
	}

	@Override
	public CommentDto editComment(CommentEditRequest request, User user) {
		Comment comment = commentRepository.findByIdOrThrow(request.getId());
		ensureCanEditComment(user, comment);
		comment.setText(request.getText());
		return commentMapper.mapToDto(commentRepository.save(comment));
	}

	private void ensureCanEditComment(User user, Comment comment) {
		if (!Objects.equals(user, comment.getAuthor()))
			throw new AccessDeniedException("Must be author of the comment to edit it");
	}

	@Override
	public void deleteComment(Long id, User user) {
		Comment comment = commentRepository.findByIdOrThrow(id);
		ensureCanDeleteComment(user, comment);
		commentRepository.delete(comment);
	}

	private void ensureCanDeleteComment(User user, Comment comment) {
		if (!Objects.equals(user, comment.getAuthor()) && user.getRole() != UserRole.MODERATOR)
			throw new AccessDeniedException("Must be moderator or author of the comment to delete it");
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long postId) {
		return commentRepository.findByPost(postRepository.findByIdOrThrow(postId)).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

}
