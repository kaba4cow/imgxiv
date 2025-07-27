package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.service.query.PostQueryService;
import com.kaba4cow.imgxiv.domain.post.service.specification.PostSpecificationService;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSearchService implements PostSearchService {

	private final PostRepository postRepository;

	private final PostQueryService postQueryService;

	private final PostSpecificationService postSpecificationService;

	@Override
	public Page<Post> searchPosts(PostQueryRequest request, Pageable pageable) {
		PostQuery postQuery = postQueryService.getCompiledQuery(request.getQuery());
		PostSpecification postSpecification = postSpecificationService.getPostSpecification(postQuery);
		return postRepository.findAll(postSpecification, pageable);
	}

}
