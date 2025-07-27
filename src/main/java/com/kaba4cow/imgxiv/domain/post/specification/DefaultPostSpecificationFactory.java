package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostSpecificationFactory implements PostSpecificationFactory {

	@Override
	public PostSpecification createPostSpecification(PostQuery compiledQuery) {
		PostSpecification postSpecification = new PostSpecification(compiledQuery);
		log.info("Created PostSpecification for {}", compiledQuery);
		return postSpecification;
	}

}
