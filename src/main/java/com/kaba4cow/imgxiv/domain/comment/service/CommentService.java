package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentService {

	CommentDto createComment(CommentCreateRequest request, User author);

	CommentDto editComment(CommentEditRequest request);

	void deleteComment(Long id);

	List<CommentDto> getCommentsByPost(Long postId);

}
