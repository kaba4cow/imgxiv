package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.NormalizedPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryCompiler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostQueryService implements PostQueryService {

	private final PostQueryCompiler postQueryCompiler;

	@Cacheable(value = CacheConfig.POST_QUERY, key = "#normalizedQuery")
	@Override
	public CompiledPostQuery getCompiledQuery(NormalizedPostQuery normalizedQuery) {
		return postQueryCompiler.compileQuery(normalizedQuery);
	}

}
