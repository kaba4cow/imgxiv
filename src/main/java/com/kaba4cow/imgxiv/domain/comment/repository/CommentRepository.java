package com.kaba4cow.imgxiv.domain.comment.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.comment.model.Comment;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.user.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post, Pageable pageable);

	long countByPost(Post post);

	List<Comment> findByAuthor(User author);

	long countByAuthor(User author);

	default Comment findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Comment", id));
	}

}
