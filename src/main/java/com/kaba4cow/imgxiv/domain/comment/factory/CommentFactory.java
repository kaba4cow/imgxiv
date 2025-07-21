package com.kaba4cow.imgxiv.domain.comment.factory;

import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentFactory {

	Comment createComment(CommentCreateRequest request, User author);

}
