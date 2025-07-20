package com.kaba4cow.imgxiv.domain.post.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostQueryNormalizer implements PostQueryNormalizer {

	@Override
	public NormalizedPostQuery normalizeQuery(String query) {
		QueryBuilder builder = new QueryBuilder();
		List<String> tags = splitQuery(query);
		for (String tag : tags)
			builder.addTag(tag);
		NormalizedPostQuery postQuery = builder.build();
		log.info("Normalized query '{}' to {}", query, postQuery);
		return postQuery;
	}

	private List<String> splitQuery(String query) {
		return Arrays.asList(query//
				.toLowerCase()//
				.split("\\s+")//
		);
	}

	private static class QueryBuilder {

		private final List<String> tags = new ArrayList<>();

		private void addTag(String tag) {
			tags.add(tag);
		}

		private NormalizedPostQuery build() {
			return new NormalizedPostQuery(tags);
		}

	}

}
