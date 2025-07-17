package com.kaba4cow.imgxiv.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kaba4cow.imgxiv.domain.user.User;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	List<Post> findByAuthor(User author);

}
