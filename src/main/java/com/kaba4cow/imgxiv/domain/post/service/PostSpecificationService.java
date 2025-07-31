package com.kaba4cow.imgxiv.domain.post.service;

import com.kaba4cow.imgxiv.domain.post.specification.PostSpecification;

public interface PostSpecificationService {

	PostSpecification getPostSpecification(String query);

}
