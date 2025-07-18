package com.kaba4cow.imgxiv.domain.post.service;

import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.common.dto.PaginationRequest;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.PostRepository;
import com.kaba4cow.imgxiv.domain.post.dto.PostQueryRequest;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostQueryExecutorService implements PostQueryExecutorService {

	private final PostRepository postRepository;

	private final PostSpecificationService postSpecificationService;

	@Override
	public Stream<Post> executeQuery(PostQueryRequest request) {
		PostSpecification postSpecification = postSpecificationService.getSpecification(request.getQuery());
		PageRequest pageRequest = getPageRequest(request);
		return postRepository.findAll(postSpecification, pageRequest).stream();
	}

	private PageRequest getPageRequest(PostQueryRequest request) {
		PaginationRequest pagination = request.getPagination();
		Sort sort = getSort(request);
		return PageRequest.of(pagination.getPage(), pagination.getSize(), sort);
	}

	private Sort getSort(PostQueryRequest request) {
		return Sort.by(request.getSorting().getDirection(), "createdAt.timestamp");
	}

}
