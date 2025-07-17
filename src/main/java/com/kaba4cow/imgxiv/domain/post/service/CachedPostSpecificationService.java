package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecificationCompiler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CachedPostSpecificationService implements PostSpecificationService {

	private final PostSpecificationCompiler postSpecificationCompiler;

	@Cacheable(value = "postSpecification", key = "#query")
	@Override
	public PostSpecification getSpecification(String query) {
		return postSpecificationCompiler.compile(query);
	}

}
