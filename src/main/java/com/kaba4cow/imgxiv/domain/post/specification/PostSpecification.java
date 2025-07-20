package com.kaba4cow.imgxiv.domain.post.specification;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.link.posttag.PostTag;
import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.tag.Tag;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.Getter;

@Getter
public class PostSpecification implements Specification<Post> {

	private static final long serialVersionUID = 1L;

	private final Set<String> requiredTags;

	private final Set<String> excludedTags;

	public PostSpecification(Collection<String> requiredTags, Collection<String> excludedTags) {
		this.requiredTags = Set.copyOf(requiredTags);
		this.excludedTags = Set.copyOf(excludedTags);
	}

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return cb.and(//
				toRequiredPredicate(root, query, cb), //
				toExcludedPredicate(root, query, cb)//
		);
	}

	private Predicate toRequiredPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (requiredTags.isEmpty())
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
		return subquery.select(idOf(postTag.get("post")))//
				.where(//
						cb.equal(nameOf(postTag.get("tag")), tag), //
						cb.equal(idOf(postTag.get("post")), idOf(root))//
				);
	}

	private Predicate toExcludedPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return excludedTags.isEmpty()//
				? cb.conjunction()//
				: cb.not(cb.exists(toExcludedSubquery(root, query, cb)));
	}

	private Subquery<Long> toExcludedSubquery(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<PostTag> postTag = subquery.from(PostTag.class);
		return subquery.select(idOf(postTag.get("post")))//
				.where(//
						cb.equal(idOf(postTag.get("post")), idOf(root)), //
						nameOf(postTag.get("tag")).in(excludedTags)//
				);
	}

	private Path<String> nameOf(Path<Tag> tag) {
		return tag.get("nameAndDescription").get("name");
	}

	private Path<Long> idOf(Path<?> entity) {
		return entity.get("id");
	}

}
