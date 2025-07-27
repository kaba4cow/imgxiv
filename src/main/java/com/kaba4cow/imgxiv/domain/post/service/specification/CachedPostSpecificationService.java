package com.kaba4cow.imgxiv.domain.post.service.specification;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostSpecificationService implements PostSpecificationService {

	@Cacheable(value = CacheConfig.POST_SPECIFICATION, key = "#compiledQuery")
	@Override
	public PostSpecification getPostSpecification(PostQuery postQuery) {
		return new PostSpecification(postQuery);
	}

}
