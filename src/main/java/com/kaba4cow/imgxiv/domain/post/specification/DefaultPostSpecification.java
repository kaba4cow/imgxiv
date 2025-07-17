package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.Collections;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.post.Post;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DefaultPostSpecification implements PostSpecification {

	private static final long serialVersionUID = 1L;

	private final Set<String> requiredTags;

	private final Set<String> excludedTags;

	public DefaultPostSpecification(Set<String> requiredTags, Set<String> excludedTags) {
		this.requiredTags = Collections.unmodifiableSet(requiredTags);
		this.excludedTags = Collections.unmodifiableSet(excludedTags);
	}

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return null;
	}

}
