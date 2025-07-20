package com.kaba4cow.imgxiv.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kaba4cow.imgxiv.common.repository.ExtendedJpaRepository;
import com.kaba4cow.imgxiv.domain.user.User;

public interface PostRepository extends ExtendedJpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	List<Post> findByAuthor(User author);

}
