package com.kaba4cow.imgxiv.domain.comment;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post, PageRequest pageRequest);

	long countByPost(Post post);

	List<Comment> findByAuthor(User author);

	long countByAuthor(User author);

	default Comment findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Comment", id));
	}

}
