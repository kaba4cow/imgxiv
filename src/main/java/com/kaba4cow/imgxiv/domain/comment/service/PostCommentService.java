package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostCommentService {

	CommentDto createComment(Long id, CommentCreateRequest request, User author);

	List<CommentDto> getCommentsByPost(Long id, Pageable pageable);

}
