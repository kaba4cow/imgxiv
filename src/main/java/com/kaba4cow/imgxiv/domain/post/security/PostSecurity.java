package com.kaba4cow.imgxiv.domain.post.security;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.post.policy.IsPostDeletable;
import com.kaba4cow.imgxiv.domain.post.policy.IsPostEditable;

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
