package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.post.Post;
import com.kaba4cow.imgxiv.domain.post.query.PostQuery;

public interface PostSpecificationService {

	Specification<Post> getPostSpecification(PostQuery postQuery);

}
