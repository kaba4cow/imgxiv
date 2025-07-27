package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentMapper;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPostCommentService implements PostCommentService {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	@Override
	public CommentDto createComment(Long postId, CommentTextRequest request, User author) {
		Comment saved = commentRepository.save(Comment.builder()//
				.post(postRepository.findByIdOrThrow(postId))//
				.author(author)//
				.text(request.getText())//
				.build());
		log.info("Created new comment: {}", saved);
		return commentMapper.mapToDto(saved);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long postId, Pageable pageable) {
		return commentRepository.findByPost(postRepository.findByIdOrThrow(postId), pageable).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

}
