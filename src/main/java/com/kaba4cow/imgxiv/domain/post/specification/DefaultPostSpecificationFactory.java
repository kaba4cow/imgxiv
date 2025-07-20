package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultPostSpecificationFactory implements PostSpecificationFactory {

	@Override
	public Specification<Post> createPostSpecification(PostQuery postQuery) {
		PostSpecification postSpecification = new PostSpecification(postQuery);
		log.info("Created PostSpecification for {}", postQuery);
		return postSpecification;
	}

}
