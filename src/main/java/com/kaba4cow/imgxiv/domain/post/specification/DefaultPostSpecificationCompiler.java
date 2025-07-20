package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.Post;

@Component
public class DefaultPostSpecificationCompiler implements PostSpecificationCompiler {

	@Override
	public Specification<Post> compile(String query) {
		SpecificationBuilder builder = new SpecificationBuilder();
		List<String> tags = Arrays.asList(query.split("\\s+"));
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

	private static class SpecificationBuilder {

		private final Set<String> requiredTags = new HashSet<>();

		private final Set<String> excludedTags = new HashSet<>();

		public void requireTag(String tag) {
			if (excludedTags.contains(tag))
				excludedTags.remove(tag);
			requiredTags.add(tag);
		}

		public void excludeTag(String tag) {
			if (requiredTags.contains(tag))
				requiredTags.remove(tag);
			excludedTags.add(tag);
		}

		public Specification<Post> build() {
			return new DefaultPostSpecification(requiredTags, excludedTags);
		}

	}

}
