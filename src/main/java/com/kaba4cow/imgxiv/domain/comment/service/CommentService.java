package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentService {

	CommentDto createComment(Long postId, CommentCreateRequest request, User author);

	CommentDto editComment(Long id, CommentEditRequest request);

	void deleteComment(Long id);

	List<CommentDto> getCommentsByPost(Long postId);

}
