package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PageRequestExtractor;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryExecutorService implements PostQueryExecutorService {

	private final PostRepository postRepository;

	private final PostSpecificationService postSpecificationService;

	private final PageRequestExtractor pageRequestExtractor;

	@Override
	public Stream<Post> executeQuery(PostQueryRequest request) {
		return postRepository.findAll(//
				postSpecificationService.getSpecification(request.getQuery()), //
				pageRequestExtractor.getSortedPageRequest(request)//
		).stream();
	}

}
