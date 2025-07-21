package com.kaba4cow.imgxiv.domain.post.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PostSecurity {

	private final PostRepository postRepository;

	@PostAuthorize("returnObject.author.id == principal.id or hasRole('moderator')")
	public Post getPostToEdit(Long id) {
		return postRepository.findByIdOrThrow(id);
	}

	@PostAuthorize("returnObject.author.id == principal.id or hasRole('moderator')")
	public Post getPostToDelete(Long id) {
		return postRepository.findByIdOrThrow(id);
	}

}
