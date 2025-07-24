package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.NormalizedPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryNormalizer;
import com.kaba4cow.imgxiv.domain.post.service.query.PostQueryService;
import com.kaba4cow.imgxiv.domain.post.service.specification.PostSpecificationService;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryExecutorService implements PostQueryExecutorService {

	private final PostRepository postRepository;

	private final PostQueryNormalizer postQueryNormalizer;

	private final PostQueryService postQueryService;

	private final PostSpecificationService postSpecificationService;

	@Override
	public Stream<Post> executeQuery(PostQueryRequest request, Pageable pageable) {
		NormalizedPostQuery normalizedQuery = postQueryNormalizer.normalizeQuery(request.getQuery());
		CompiledPostQuery compiledQuery = postQueryService.getCompiledQuery(normalizedQuery);
		PostSpecification postSpecification = postSpecificationService.getPostSpecification(compiledQuery);
		return postRepository.findAll(//
				postSpecification, //
				pageable//
		).stream();
	}

}
