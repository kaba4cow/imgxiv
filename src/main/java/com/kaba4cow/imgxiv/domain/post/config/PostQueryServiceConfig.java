package com.kaba4cow.imgxiv.domain.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kaba4cow.imgxiv.domain.post.service.query.CachedPostQueryService;
import com.kaba4cow.imgxiv.domain.post.service.query.DefaultPostQueryService;
import com.kaba4cow.imgxiv.domain.post.service.query.PostQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PostQueryServiceConfig {

	@Bean
	public PostQueryService postQueryService(//
			DefaultPostQueryService defaultService, //
			CachedPostQueryService cachedService, //
			@Value("${feature.posts.query-caching.enabled:false}") boolean enableCaching) {
		return enableCaching//
				? cachedService//
				: defaultService;
	}

}
