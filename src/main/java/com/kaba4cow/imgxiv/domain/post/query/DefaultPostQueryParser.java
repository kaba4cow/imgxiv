package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DefaultPostQueryParser implements PostQueryParser {

	private final Pattern pattern = Pattern.compile("^!*[a-zA-Z0-9_]+$");

	@Override
	public CompiledPostQuery parse(String query) {
		QueryBuilder builder = new QueryBuilder();
		List<String> tokens = splitQuery(query);
		for (String token : tokens)
			if (isTokenValid(token)) {
				boolean exclude = false;
				while (isTokenNegating(token)) {
					exclude = !exclude;
					token = token.substring(1);
				}
				if (!token.isBlank())
					if (exclude)
						builder.excludeTag(token);
					else
						builder.requireTag(token);
			}
		return builder.build();
	}

	private boolean isTokenNegating(String token) {
		return !token.isBlank() && token.startsWith("!");
	}

	private boolean isTokenValid(String token) {
		return pattern.matcher(token).matches();
	}

	private List<String> splitQuery(String query) {
		return Arrays.asList(query//
				.toLowerCase()//
				.split("\\s+")//
		);
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
