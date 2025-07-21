package com.kaba4cow.imgxiv.domain.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kaba4cow.imgxiv.domain.post.service.specification.CachedPostSpecificationService;
import com.kaba4cow.imgxiv.domain.post.service.specification.DefaultPostSpecificationService;
import com.kaba4cow.imgxiv.domain.post.service.specification.PostSpecificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PostSpecificationServiceConfig {

	@Bean
	public PostSpecificationService postSpecificationService(//
			DefaultPostSpecificationService defaultService, //
			CachedPostSpecificationService cachedService, //
			@Value("${feature.posts.specification-caching.enabled:false}") boolean enableCaching) {
		return enableCaching//
				? cachedService//
				: defaultService;
	}

}
