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
	public CompiledPostQuery compile(NormalizedPostQuery normalizedPostQuery) {
		QueryBuilder builder = new QueryBuilder();
		for (String tag : normalizedPostQuery.getTags()) {
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
		CompiledPostQuery compiledPostQuery = builder.build();
		log.info("Compiled query {} to {}", normalizedPostQuery, compiledPostQuery);
		return compiledPostQuery;
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
