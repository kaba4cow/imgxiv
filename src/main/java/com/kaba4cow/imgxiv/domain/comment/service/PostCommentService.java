package com.kaba4cow.imgxiv.domain.comment.service;

import java.util.List;

import com.kaba4cow.imgxiv.common.dto.PaginationParams;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostCommentService {

	CommentDto createComment(Long id, CommentCreateRequest request, User author);

	List<CommentDto> getCommentsByPost(Long id, PaginationParams pagination);

}
