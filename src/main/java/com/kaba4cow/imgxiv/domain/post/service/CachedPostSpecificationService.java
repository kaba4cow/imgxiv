package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecificationFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostSpecificationService implements PostSpecificationService {

	private final PostSpecificationFactory postSpecificationFactory;

	@Cacheable(value = CacheConfig.POST_SPECIFICATION, key = "#postQuery")
	@Override
	public PostSpecification getPostSpecification(CompiledPostQuery postQuery) {
		return postSpecificationFactory.createPostSpecification(postQuery);
	}

}
