package com.kaba4cow.imgxiv.domain.post.service.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.config.CacheConfig;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.post.query.PostQueryParser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostQueryService implements PostQueryService {

	private final PostQueryParser postQueryParser;

	@Cacheable(value = CacheConfig.POST_QUERY, key = "#normalizedQuery")
	@Override
	public CompiledPostQuery getCompiledQuery(String query) {
		return postQueryParser.parse(query);
	}

}
