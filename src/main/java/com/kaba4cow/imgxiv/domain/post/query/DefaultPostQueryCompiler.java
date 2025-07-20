package com.kaba4cow.imgxiv.domain.post.query;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostQueryCompiler implements PostQueryCompiler {

	@Override
	public CompiledPostQuery compileQuery(NormalizedPostQuery normalizedQuery) {
		QueryBuilder builder = new QueryBuilder();
		for (String token : normalizedQuery.getTokens()) {
			boolean exclude = false;
			while (!token.isBlank() && token.startsWith("!")) {
				exclude = !exclude;
				token = token.substring(1);
			}
			if (!token.isBlank())
				if (exclude)
					builder.excludeTag(token);
				else
					builder.requireTag(token);
		}
		CompiledPostQuery compiledQuery = builder.build();
		log.info("Compiled query {} to {}", normalizedQuery, compiledQuery);
		return compiledQuery;
	}

	private static class QueryBuilder {

		private final Set<String> requiredTags = new HashSet<>();

		private final Set<String> excludedTags = new HashSet<>();

		private void requireTag(String tag) {
			if (excludedTags.contains(tag))
				excludedTags.remove(tag);
			requiredTags.add(tag);
		}

		private void excludeTag(String tag) {
			if (requiredTags.contains(tag))
				requiredTags.remove(tag);
			excludedTags.add(tag);
		}

		private CompiledPostQuery build() {
			return new CompiledPostQuery(requiredTags, excludedTags);
		}

	}

}
