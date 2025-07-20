package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PageRequestExtractor;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryExecutorService implements PostQueryExecutorService {

	private final PostRepository postRepository;

	private final PostQueryService postQueryService;

	private final PostSpecificationService postSpecificationService;

	private final PageRequestExtractor pageRequestExtractor;

	@Override
	public Stream<Post> executeQuery(PostQueryRequest request) {
		PostQuery postQuery = postQueryService.getPostQuery(request.getQuery());
		PostSpecification postSpecification = postSpecificationService.getPostSpecification(postQuery);
		return postRepository.findAll(//
				postSpecification, //
				pageRequestExtractor.getPageRequest(request, "createdAt.timestamp")//
		).stream();
	}

}
