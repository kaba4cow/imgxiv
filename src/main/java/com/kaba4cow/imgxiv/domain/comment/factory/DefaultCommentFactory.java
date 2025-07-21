package com.kaba4cow.imgxiv.domain.comment.factory;

import com.kaba4cow.imgxiv.common.annotation.Factory;
import com.kaba4cow.imgxiv.domain.comment.Comment;
import com.kaba4cow.imgxiv.domain.comment.CommentRepository;
import com.kaba4cow.imgxiv.domain.comment.dto.CommentCreateRequest;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.user.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Factory
public class DefaultCommentFactory implements CommentFactory {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	@Override
	public Comment createComment(CommentCreateRequest request, User author) {
		Comment comment = new Comment();
		comment.setPost(postRepository.findByIdOrThrow(request.getPostId()));
		comment.setAuthor(author);
		comment.setText(request.getText());
		Comment saved = commentRepository.save(comment);
		log.info("Created new comment: {}", saved);
		return saved;
	}

}
