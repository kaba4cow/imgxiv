package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostSpecification implements Specification<Post> {

	private static final long serialVersionUID = 1L;

	private final Set<String> requiredTags;

	private final Set<String> excludedTags;

	public PostSpecification(Set<String> requiredTags, Set<String> excludedTags) {
		this.requiredTags = Set.copyOf(requiredTags);
		this.excludedTags = Set.copyOf(excludedTags);
	}

	public static PostSpecificationBuilder builder() {
		return new PostSpecificationBuilder();
	}

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return cb.and(//
				toRequiredPredicate(root, query, cb), //
				toExcludedPredicate(root, query, cb)//
		);
	}

	private Predicate toRequiredPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (!hasRequiredTags())
			return cb.conjunction();
		Predicate requiredPredicate = cb.conjunction();
		for (String tag : requiredTags)
			requiredPredicate = cb.and(//
					requiredPredicate, //
					cb.exists(toRequiredSubquery(tag, root, query, cb))//
			);
		return requiredPredicate;
	}

	private Subquery<Long> toRequiredSubquery(String tag, Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<PostTag> postTag = subquery.from(PostTag.class);
		return subquery.select(postIdOf(postTag))//
				.where(//
						cb.equal(tagNameOf(postTag), tag), //
						cb.equal(postIdOf(postTag), idOf(root))//
				);
	}

	private Predicate toExcludedPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return hasExcludedTags()//
				? cb.not(cb.exists(toExcludedSubquery(root, query, cb)))//
				: cb.conjunction();
	}

	private Subquery<Long> toExcludedSubquery(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<PostTag> postTag = subquery.from(PostTag.class);
		return subquery.select(postIdOf(postTag))//
				.where(//
						cb.equal(postIdOf(postTag), idOf(root)), //
						tagNameOf(postTag).in(excludedTags)//
				);
	}

	private Path<Long> postIdOf(Path<PostTag> postTag) {
		return postTag.get("post").get("id");
	}

	private Path<String> tagNameOf(Path<PostTag> postTag) {
		return postTag.get("tag").get("name");
	}

	private Path<Long> idOf(Path<?> path) {
		return path.get("id");
	}

	private boolean hasRequiredTags() {
		return !requiredTags.isEmpty();
	}

	private boolean hasExcludedTags() {
		return !excludedTags.isEmpty();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class PostSpecificationBuilder {

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

		public PostSpecification build() {
			return new PostSpecification(requiredTags, excludedTags);
		}

	}

}
