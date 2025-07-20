package com.kaba4cow.imgxiv.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kaba4cow.imgxiv.common.repository.ExtendedJpaRepository;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.user.User;

public interface CommentRepository extends ExtendedJpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.postAndUser.post = :post")
	List<Comment> findByPost(@Param("post") Post post);

	@Query("SELECT c FROM Comment c WHERE c.postAndUser.user = :user")
	List<Comment> findByUser(@Param("user") User user);

}
