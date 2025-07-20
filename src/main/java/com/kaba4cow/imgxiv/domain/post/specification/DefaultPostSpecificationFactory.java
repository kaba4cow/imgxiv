package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostSpecificationFactory implements PostSpecificationFactory {

	@Override
	public PostSpecification createPostSpecification(CompiledPostQuery postQuery) {
		PostSpecification postSpecification = new PostSpecification(postQuery);
		log.info("Created PostSpecification for {}", postQuery);
		return postSpecification;
	}

}
