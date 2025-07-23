package com.kaba4cow.imgxiv.domain.post.security;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.auth.annotation.policy.IsPostDeletable;
import com.kaba4cow.imgxiv.auth.annotation.policy.IsPostEditable;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PostSecurity {

	private final PostRepository postRepository;

	@IsPostEditable
	public Post getPostToEdit(Long id) {
		return postRepository.findByIdOrThrow(id);
	}

	@IsPostDeletable
	public Post getPostToDelete(Long id) {
		return postRepository.findByIdOrThrow(id);
	}

}
