package com.kaba4cow.imgxiv.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

	public static final String POST_SPECIFICATION = "postSpecification";

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(POST_SPECIFICATION);
	}

}
