package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.query.CompiledPostQuery;
import com.kaba4cow.imgxiv.domain.tag.Tag;

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

	private final CompiledPostQuery postQuery;

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
		return subquery.select(idOf(postTag.get("post")))//
				.where(//
						cb.equal(nameOf(postTag.get("tag")), tag), //
						cb.equal(idOf(postTag.get("post")), idOf(root))//
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
		return subquery.select(idOf(postTag.get("post")))//
				.where(//
						cb.equal(idOf(postTag.get("post")), idOf(root)), //
						nameOf(postTag.get("tag")).in(postQuery.getExcludedTags())//
				);
	}

	private Path<String> nameOf(Path<Tag> tag) {
		return tag.get("nameAndDescription").get("name");
	}

	private Path<Long> idOf(Path<?> entity) {
		return entity.get("id");
	}

}
