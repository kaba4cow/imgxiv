package com.kaba4cow.imgxiv.domain.comment.service;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentEditRequest;

public interface CommentService {

	CommentDto editComment(Long id, CommentEditRequest request);

	void deleteComment(Long id);

}
