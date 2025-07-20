package com.kaba4cow.imgxiv.domain.post.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

public interface PostSpecificationFactory {

	Specification<Post> createPostSpecification(PostQuery query);

}
