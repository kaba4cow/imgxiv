package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecificationCompiler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CachedPostSpecificationService implements PostSpecificationService {

	private final PostSpecificationCompiler postSpecificationCompiler;

	@Cacheable(value = CacheConfig.POST_SPECIFICATION, key = "#query")
	@Override
	public PostSpecification getSpecification(String query) {
		PostSpecification postSpecification = postSpecificationCompiler.compile(query);
		log.info("Compiled specification for query: {}", query);
		return postSpecification;
	}

}
