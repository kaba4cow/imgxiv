package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.model.Post;
import com.kaba4cow.imgxiv.domain.post.repository.PostRepository;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSearchService implements PostSearchService {

	private final PostRepository postRepository;

	private final PostSpecificationService postSpecificationService;

	@Override
	public Page<Post> searchPosts(PostQueryRequest request, Pageable pageable) {
		PostSpecification postSpecification = postSpecificationService.getPostSpecification(request.getQuery());
		return postRepository.findAll(postSpecification, pageable);
	}

}
