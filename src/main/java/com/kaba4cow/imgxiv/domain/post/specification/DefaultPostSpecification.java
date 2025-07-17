package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.Collection;
import java.util.Set;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.tag.Tag;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class DefaultPostSpecification implements PostSpecification {

	private static final long serialVersionUID = 1L;

	private final Set<String> requiredTags;

	private final Set<String> excludedTags;

	public DefaultPostSpecification(Collection<String> requiredTags, Collection<String> excludedTags) {
		this.requiredTags = Set.copyOf(requiredTags);
		this.excludedTags = Set.copyOf(excludedTags);
	}

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Join<Post, PostTag> postTags = root.join("postTags", JoinType.LEFT);
		Join<PostTag, Tag> tag = postTags.join("tag");
		return cb.and(//
				createRequiredPredicate(root, query, cb), //
				createExcludedPredicate(nameOf(tag), cb)//
		);
	}

	private Predicate createRequiredPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate requiredPredicate = cb.conjunction();
		if (requiredTags.isEmpty())
			return requiredPredicate;
		for (String tag : requiredTags)
			requiredPredicate = cb.and(//
					requiredPredicate, //
					cb.exists(createSubquery(tag, root, query, cb))//
			);
		return requiredPredicate;
	}

	private Subquery<Long> createSubquery(String tag, Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<PostTag> postTag = subquery.from(PostTag.class);
		return subquery.select(idOf(postTag.get("post")))//
				.where(//
						cb.equal(nameOf(postTag.get("tag")), tag), //
						cb.equal(idOf(postTag.get("post")), idOf(root))//
				);
	}

	private Predicate createExcludedPredicate(Path<String> tag, CriteriaBuilder cb) {
		return excludedTags.isEmpty()//
				? cb.conjunction()//
				: cb.not(tag.in(excludedTags));
	}

	private Path<String> nameOf(Path<Tag> tag) {
		return tag.get("nameAndDescription").get("name");
	}

	private Path<Long> idOf(Path<?> entity) {
		return entity.get("id");
	}

}
