package com.kaba4cow.imgxiv.domain.post.query;

import java.util.Collection;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PostQuery {

	private final Set<String> requiredTags;

	private final Set<String> excludedTags;

	public PostQuery(Collection<String> requiredTags, Collection<String> excludedTags) {
		this.requiredTags = Set.copyOf(requiredTags);
		this.excludedTags = Set.copyOf(excludedTags);
	}

	public boolean hasRequiredTags() {
		return !requiredTags.isEmpty();
	}

	public boolean hasExcludedTags() {
		return !excludedTags.isEmpty();
	}

}
