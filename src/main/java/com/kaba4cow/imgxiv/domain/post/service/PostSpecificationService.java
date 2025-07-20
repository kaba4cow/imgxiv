package com.kaba4cow.imgxiv.domain.post.service;

import org.springframework.data.jpa.domain.Specification;

import com.kaba4cow.imgxiv.domain.post.Post;

public interface PostSpecificationService {

	Specification<Post> getSpecification(String query);

}
