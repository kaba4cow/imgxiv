package com.kaba4cow.imgxiv.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.post = :post")
	List<Comment> findByPost(@Param("post") Post post);

	@Query("SELECT c FROM Comment c WHERE c.author = :author")
	List<Comment> findByAuthor(@Param("author") User author);

	default Comment findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException(String.format("Comment not found: %s", id)));
	}

}
