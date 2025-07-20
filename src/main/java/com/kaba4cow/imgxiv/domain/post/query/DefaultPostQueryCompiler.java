package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostQueryCompiler implements PostQueryCompiler {

	@Override
	public CompiledPostQuery compile(String query) {
		QueryBuilder builder = new QueryBuilder();
		List<String> tags = splitQuery(query);
		for (String tag : tags) {
			boolean exclude = false;
			while (!tag.isBlank() && tag.startsWith("!")) {
				exclude = !exclude;
				tag = tag.substring(1);
			}
			if (!tag.isBlank())
				if (exclude)
					builder.excludeTag(tag);
				else
					builder.requireTag(tag);
		}
		CompiledPostQuery postQuery = builder.build();
		log.info("Compiled query '{}' to {}", query, postQuery);
		return postQuery;
	}

	private List<String> splitQuery(String query) {
		return Arrays.stream(query.split("\\s+"))//
				.distinct()//
				.toList();
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
