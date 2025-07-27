package com.kaba4cow.imgxiv.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder()//
				.initialCapacity(100)//
				.maximumSize(1000L)//
				.expireAfterAccess(5L, TimeUnit.MINUTES)//
		);
		return cacheManager;
	}

}
