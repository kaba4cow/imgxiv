package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

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
		return commentMapper.mapToDto(PersistLog.log(saveComment(request, user)));
	}

	private Comment saveComment(CommentCreateRequest request, User user) {
		Comment comment = new Comment();
		comment.getPostAndUser().setPost(postRepository.findByIdOrThrow(request.getPostId()));
		comment.getPostAndUser().setUser(user);
		comment.setText(request.getText());
		return commentRepository.save(comment);
	}

	@Override
	public CommentDto editComment(CommentEditRequest request, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CommentDto deleteComment(Long id, User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long postId) {
		return commentRepository.findByPost(postRepository.findByIdOrThrow(postId)).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

}
