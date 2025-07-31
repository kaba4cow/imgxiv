package com.kaba4cow.imgxiv.domain.comment.service;

import com.kaba4cow.imgxiv.domain.comment.dto.CommentDto;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentTextRequest;

public interface CommentService {

	CommentDto editComment(Long id, CommentTextRequest request);

	void deleteComment(Long id);

}
