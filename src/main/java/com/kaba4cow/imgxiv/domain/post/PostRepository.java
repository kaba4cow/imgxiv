package com.kaba4cow.imgxiv.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kaba4cow.imgxiv.common.exception.NotFoundException;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.user.model.User;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	List<Post> findByAuthor(User author);

	default Post findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new NotFoundException("Post", id));
	}

}
