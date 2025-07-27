package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecificationParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSpecificationService implements PostSpecificationService {

	private final PostSpecificationParser postSpecificationParser;

	@Override
	public PostSpecification getPostSpecification(String query) {
		return postSpecificationParser.parsePostSpecification(query);
	}

}
