package com.kaba4cow.imgxiv.domain.post.query;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DefaultPostQueryNormalizer implements PostQueryNormalizer {

	@Override
	public String normalizeQuery(String query) {
		return query.toLowerCase();
	}

}
