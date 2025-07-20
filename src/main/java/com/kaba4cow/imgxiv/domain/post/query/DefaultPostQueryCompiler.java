package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DefaultPostQueryCompiler implements PostQueryCompiler {

	@Override
	public PostQuery compile(String query) {
		QueryBuilder builder = new QueryBuilder();
		List<String> tags = splitQuery(normalizeQuery(query));
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
		return builder.build();
	}

	private List<String> splitQuery(String query) {
		return Arrays.stream(query.split("\\s+"))//
				.distinct()//
				.toList();
	}

	private String normalizeQuery(String query) {
		return query.toLowerCase();
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

		private PostQuery build() {
			return new PostQuery(requiredTags, excludedTags);
		}

	}

}
