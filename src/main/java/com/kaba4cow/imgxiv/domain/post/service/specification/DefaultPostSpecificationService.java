package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSpecificationService implements PostSpecificationService {

	@Override
	public PostSpecification getPostSpecification(PostQuery postQuery) {
		return new PostSpecification(postQuery);
	}

}
