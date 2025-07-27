package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecificationFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSpecificationService implements PostSpecificationService {

	private final PostSpecificationFactory postSpecificationFactory;

	@Override
	public PostSpecification getPostSpecification(PostQuery query) {
		return postSpecificationFactory.createPostSpecification(query);
	}

}
