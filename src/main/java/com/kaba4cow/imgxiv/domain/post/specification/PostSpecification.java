package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostSpecification implements Specification<Post> {

	private static final long serialVersionUID = 1L;

	private final PostQuery postQuery;

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return cb.and(//
				toRequiredPredicate(root, query, cb), //
				toExcludedPredicate(root, query, cb)//
		);
	}

	private Predicate toRequiredPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (!postQuery.hasRequiredTags())
			return cb.conjunction();
		Predicate requiredPredicate = cb.conjunction();
		for (String tag : postQuery.getRequiredTags())
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
		return postQuery.hasExcludedTags()//
				? cb.not(cb.exists(toExcludedSubquery(root, query, cb)))//
				: cb.conjunction();
	}

	private Subquery<Long> toExcludedSubquery(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<PostTag> postTag = subquery.from(PostTag.class);
		return subquery.select(postIdOf(postTag))//
				.where(//
						cb.equal(postIdOf(postTag), idOf(root)), //
						tagNameOf(postTag).in(postQuery.getExcludedTags())//
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

}
