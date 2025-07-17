package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.Collections;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.tag.Tag;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

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
		Join<Post, PostTag> postTags = root.join("postTags", JoinType.LEFT);
		Join<PostTag, Tag> tag = postTags.join("tag");
		Predicate requiredPredicate = cb.conjunction();
		for (String name : requiredTags) {
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<PostTag> pt = subquery.from(PostTag.class);
			subquery.select(pt.get("post").get("id"))//
					.where(//
							cb.equal(pt.get("tag").get("name"), name), //
							cb.equal(pt.get("post").get("id"), root.get("id"))//
					);
			requiredPredicate = cb.and(requiredPredicate, cb.exists(subquery));
		}
		Predicate excludedPredicate = cb.conjunction();
		if (!excludedTags.isEmpty())
			excludedPredicate = cb.not(tag.get("name").in(excludedTags));
		return cb.and(requiredPredicate, excludedPredicate);
	}

}
