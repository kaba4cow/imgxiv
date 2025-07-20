package com.kaba4cow.imgxiv.domain.post.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostQueryNormalizer implements PostQueryNormalizer {

	private final Pattern pattern = Pattern.compile("^!*[a-zA-Z0-9_]+$");

	@Override
	public NormalizedPostQuery normalizeQuery(String query) {
		QueryBuilder builder = new QueryBuilder();
		List<String> tokens = splitQuery(query);
		for (String token : tokens)
			if (isValid(token))
				builder.addToken(token);
		NormalizedPostQuery normalizedQuery = builder.build();
		log.info("Normalized query '{}' to {}", query, normalizedQuery);
		return normalizedQuery;
	}

	private boolean isValid(String token) {
		return pattern.matcher(token).matches();
	}

	private List<String> splitQuery(String query) {
		return Arrays.asList(query//
				.toLowerCase()//
				.split("\\s+")//
		);
	}

	private static class QueryBuilder {

		private final List<String> tokens = new ArrayList<>();

		private void addToken(String token) {
			tokens.add(token);
		}

		private NormalizedPostQuery build() {
			return new NormalizedPostQuery(tokens);
		}

	}

}
