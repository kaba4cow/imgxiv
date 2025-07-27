package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryParser;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostSpecificationService implements PostSpecificationService {

	private final PostQueryParser postSpecificationParser;

	@Cacheable(value = CacheConfig.POST_SPECIFICATION, key = "#compiledQuery")
	@Override
	public PostSpecification getPostSpecification(String query) {
		return postSpecificationParser.parseQuery(query);
	}

}
