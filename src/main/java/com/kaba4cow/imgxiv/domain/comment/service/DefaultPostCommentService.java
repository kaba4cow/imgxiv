package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PageRequestExtractor;
import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentMapper;
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

	private final PageRequestExtractor pageRequestExtractor;

	@Override
	public CommentDto createComment(Long id, CommentCreateRequest request, User author) {
		Comment comment = new Comment();
		comment.setPost(postRepository.findByIdOrThrow(id));
		comment.setAuthor(author);
		comment.setText(request.getText());
		Comment saved = commentRepository.save(comment);
		log.info("Created new comment: {}", saved);
		return commentMapper.mapToDto(saved);
	}

	@Override
	public List<CommentDto> getCommentsByPost(Long id, PaginationParams pagination) {
		return commentRepository.findByPost(//
				postRepository.findByIdOrThrow(id), //
				pageRequestExtractor.getPageRequest(pagination, "createdAt.timestamp")//
		).stream()//
				.map(commentMapper::mapToDto)//
				.toList();
	}

}
