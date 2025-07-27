package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.query.PostQueryParser;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DefaultPostSpecificationService implements PostSpecificationService {

	private final PostQueryParser postSpecificationParser;

	@Override
	public PostSpecification getPostSpecification(String query) {
		return postSpecificationParser.parseQuery(query);
	}

}
