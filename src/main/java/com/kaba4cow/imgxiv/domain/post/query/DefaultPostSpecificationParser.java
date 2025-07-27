package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;
import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification.PostSpecificationBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DefaultPostSpecificationParser implements PostSpecificationParser {

	private final Pattern pattern = Pattern.compile("^!*[a-zA-Z0-9_]+$");

	@Override
	public PostSpecification parsePostSpecification(String query) {
		PostSpecificationBuilder builder = PostSpecification.builder();
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

}
